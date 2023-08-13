package org.vitaliistf.blocktracker.repositories;

import org.vitaliistf.blocktracker.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCoinId(Long id);

}
