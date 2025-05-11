package com.customerdata.batchprocess.repository;

import com.customerdata.batchprocess.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByCustomerIdOrAccountNumberOrDescription(String customerId, String accountNumber, String description, Pageable pageable);
    int countByAccountNumberAndCustomerIdAndTrxDateAndTrxTimeAndTrxAmount(String accountNumber, String customerId, LocalDate trxDate, LocalTime trxTime, Double trxAmount);
}
