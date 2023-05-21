package com.shopping.service.repository.sql;

import com.shopping.service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //TODO in nosql also
    @Query(value = "select sum(t.total) from transaction t WHERE (t.\"date\"\\:\\:date  >= (CURRENT_TIMESTAMP - interval '1 month') \\:\\: date\n) ",
            nativeQuery = true)
    double getSumOfTransactions();
}
