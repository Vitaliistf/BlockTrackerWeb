package org.vitaliistf.blocktracker.service.impl;

import org.vitaliistf.blocktracker.exception.PermissionDeniedException;
import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.Coin;
import org.vitaliistf.blocktracker.models.Transaction;
import org.vitaliistf.blocktracker.repositories.CoinRepository;
import org.vitaliistf.blocktracker.repositories.TransactionRepository;
import org.vitaliistf.blocktracker.service.CoinService;
import org.vitaliistf.blocktracker.service.PortfolioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class CoinServiceImpl implements CoinService {

    private final CoinRepository coinRepository;
    private final PortfolioService portfolioService;
    private final TransactionRepository transactionRepository;

    @Override
    public void updateCoinData(Long coinId) {

        Coin coin = coinRepository.getOne(coinId);

        List<Transaction> transactions = transactionRepository.findAllByCoinId(coinId);

        BigDecimal totalBuyAmount = BigDecimal.ZERO;
        BigDecimal totalBuyCost = BigDecimal.ZERO;

        BigDecimal totalSellAmount = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            BigDecimal amount = transaction.getAmount();
            if (transaction.isBuy()) {
                totalBuyAmount = totalBuyAmount.add(amount);
                totalBuyCost = totalBuyCost.add(
                        transaction.getPrice().multiply(amount)
                );
            } else {
                totalSellAmount = totalSellAmount.add(amount);
            }
        }

        if (totalBuyAmount.subtract(totalSellAmount).compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal averageBuyPrice = totalBuyCost.divide(totalBuyAmount, 2, RoundingMode.CEILING);
            coin.setAmount(totalBuyAmount.subtract(totalSellAmount));
            coin.setAvgBuyingPrice(averageBuyPrice);
            coinRepository.save(coin);
        } else {
            coinRepository.delete(coin);
        }
    }

    @Override
    public Coin save(Coin coin) {
        return coinRepository.save(coin);
    }

    @Override
    public Coin getById(Long id, User user) {
        Coin coin = coinRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find coin with such id.")
        );
        if (!coin.getPortfolio().getUser().equals(user)) {
            throw new PermissionDeniedException("You cannot view this coin.");
        }
        return coin;
    }

    @Override
    public List<Coin> getAllByPortfolioId(Long portfolioId, User user) {
        portfolioService.getById(portfolioId, user); //Throws exception if client has no permission
        return coinRepository.findAllByPortfolioId(portfolioId);
    }

    @Override
    public void deleteById(Long id, User user) {
        Coin coin = coinRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find coin with such id.")
        );
        if (!coin.getPortfolio().getUser().equals(user)) {
            throw new PermissionDeniedException("You cannot view this coin.");
        }
        coinRepository.deleteById(id);
    }
}