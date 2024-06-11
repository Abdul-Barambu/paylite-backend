package com.abdul.paylitebackend.flutterwave.ProcessedTransactions;

import com.abdul.paylitebackend.flutterwave.ProcessedTransactions.ProcessedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedTransactionRepository extends JpaRepository<ProcessedTransaction, Long> {
    boolean existsByTxRef(String txRef);
}
