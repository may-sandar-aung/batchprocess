package com.customerdata.batchprocess.service;

import com.customerdata.batchprocess.entity.Transaction;
import com.customerdata.batchprocess.model.TransactionDto;
import com.customerdata.batchprocess.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Retrieve transactions with pagination and filter by customerId, accountNumber, or description
    public Page<TransactionDto> getTransactions(String customerId, String accountNumber, String description, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findByCustomerIdOrAccountNumberOrDescription(customerId, accountNumber, description, pageable);
        return transactions.map(this::convertToModel); // Convert Entity to DTO
    }
    @Transactional
    public void updateDescription(Long id, String description) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setDescription(description);
    }

    // Convert Transaction Entity to TransactionModel DTO
    public TransactionDto convertToModel(Transaction transaction) {
        TransactionDto model = new TransactionDto();
        model.setId(transaction.getId());
        model.setAccountNumber(transaction.getAccountNumber());
        model.setTrxAmount(transaction.getTrxAmount());
        model.setDescription(transaction.getDescription());
        model.setTrxDate(transaction.getTrxDate().toString());
        model.setTrxTime(transaction.getTrxTime().toString());
        model.setCustomerId(transaction.getCustomerId());
        return model;
    }
}
