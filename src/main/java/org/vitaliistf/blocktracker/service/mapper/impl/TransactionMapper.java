package org.vitaliistf.blocktracker.service.mapper.impl;

import org.vitaliistf.blocktracker.dto.request.TransactionRequestDto;
import org.vitaliistf.blocktracker.dto.response.TransactionResponseDto;
import org.vitaliistf.blocktracker.models.Coin;
import org.vitaliistf.blocktracker.models.Portfolio;
import org.vitaliistf.blocktracker.models.Transaction;
import org.vitaliistf.blocktracker.service.mapper.RequestDtoMapper;
import org.vitaliistf.blocktracker.service.mapper.ResponseDtoMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionMapper implements RequestDtoMapper<TransactionRequestDto, Transaction>,
        ResponseDtoMapper<TransactionResponseDto, Transaction> {

    @Override
    public Transaction mapToModel(TransactionRequestDto dto) {
        Transaction transaction = new Transaction();

        Portfolio portfolio = new Portfolio();
        portfolio.setId(dto.getPortfolioId());

        Coin coin = new Coin();
        coin.setId(dto.getCoinId());
        coin.setSymbol(dto.getSymbol());
        coin.setAmount(BigDecimal.ZERO);
        coin.setAvgBuyingPrice(BigDecimal.ZERO);
        coin.setPortfolio(portfolio);

        transaction.setBuy(dto.isBuy());
        transaction.setAmount(dto.getAmount());
        transaction.setPrice(dto.getPrice());
        transaction.setDate(LocalDateTime.now());
        transaction.setCoin(coin);

        return transaction;
    }

    @Override
    public TransactionResponseDto mapToDto(Transaction transaction) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setId(transaction.getId());
        transactionResponseDto.setAmount(transaction.getAmount());
        transactionResponseDto.setBuy(transaction.isBuy());
        transactionResponseDto.setSymbol(transaction.getCoin().getSymbol());
        transactionResponseDto.setPrice(transaction.getPrice());
        transactionResponseDto.setDate(transaction.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return transactionResponseDto;
    }
}
