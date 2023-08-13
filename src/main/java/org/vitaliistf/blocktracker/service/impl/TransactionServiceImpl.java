package org.vitaliistf.blocktracker.service.impl;

import org.vitaliistf.blocktracker.exception.NotEnoughCoinsException;
import org.vitaliistf.blocktracker.exception.PermissionDeniedException;
import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.Coin;
import org.vitaliistf.blocktracker.models.Transaction;
import org.vitaliistf.blocktracker.repositories.TransactionRepository;
import org.vitaliistf.blocktracker.service.CoinService;
import org.vitaliistf.blocktracker.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CoinService coinService;

    @Override
    public void deleteById(Long id, User user) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find such transaction.")
        );
        if (!transaction.getCoin().getPortfolio().getUser().equals(user)) {
            throw new PermissionDeniedException("You cannot delete this transaction.");
        }
        transactionRepository.deleteById(id);
        coinService.updateCoinData(transaction.getCoin().getId());
    }

    @Override
    public Transaction editById(Long id, Transaction transaction, User user) {
        Transaction transactionFromDb = transactionRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find such transaction.")
        );
        if (!transactionFromDb.getCoin().getPortfolio().getUser().equals(user)) {
            throw new PermissionDeniedException("You cannot edit this transaction.");
        }
        transactionFromDb.setAmount(transaction.getAmount());
        transactionFromDb.setPrice(transaction.getPrice());
        transactionRepository.save(transactionFromDb);

        coinService.updateCoinData(transactionFromDb.getCoin().getId());
        return transactionFromDb;
    }

    @Override
    public List<Transaction> getByCoinId(Long coinId, User user) {
        //Throws an exception if client has no permission
        coinService.getById(coinId, user);
        return transactionRepository.findAllByCoinId(coinId);
    }

    @Override
    public Transaction getById(Long id, User user) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find such transaction.")
        );
        if (!transaction.getCoin().getPortfolio().getUser().equals(user)) {
            throw new PermissionDeniedException("You cannot view this transaction.");
        }
        return transaction;
    }

    @Transactional
    @Override
    public Transaction addTransaction(Transaction transaction, User user) {

        if (transaction.getCoin().getId() == 0) {
            transaction.setCoin(coinService.save(transaction.getCoin()));
        }
        //Throws an exception is client has no permission
        Coin coin = coinService.getById(transaction.getCoin().getId(), user);

        if (!transaction.isBuy() && coin.getAmount().compareTo(transaction.getAmount()) < 0) {
            throw new NotEnoughCoinsException("You don't have enough coins to complete this transaction.");
        }
        transaction.setCoin(coin);
        Transaction transactionToReturn = transactionRepository.save(transaction);
        coinService.updateCoinData(transaction.getCoin().getId());
        return transactionToReturn;
    }
}
