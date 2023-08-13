package org.vitaliistf.blocktracker.service;

import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.Transaction;

import java.util.List;

public interface TransactionService {
    void deleteById(Long id, User user);

    Transaction editById(Long id, Transaction transaction, User user);

    List<Transaction> getByCoinId(Long coinId, User user);

    Transaction getById(Long id, User user);

    Transaction addTransaction(Transaction transaction, User user);
}
