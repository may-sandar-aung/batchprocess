package com.customerdata.batchprocess.writer;

import com.customerdata.batchprocess.entity.Transaction;
import com.customerdata.batchprocess.model.TransactionDto;
import com.customerdata.batchprocess.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionWriter implements ItemWriter<TransactionDto> {
    private static final Logger logger = LoggerFactory.getLogger(TransactionWriter.class);

    @Autowired
    private TransactionRepository repository;

    @Override
    public void write(Chunk<? extends TransactionDto> chunk) {
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionDto model : chunk) {
            Transaction entity = convertToEntity(model);
            transactions.add(entity);
        }
        repository.saveAll(transactions);
    }
    private Transaction convertToEntity(TransactionDto model) {
        Transaction entity = new Transaction();
        entity.setAccountNumber(model.getAccountNumber());
        entity.setTrxAmount(model.getTrxAmount());
        entity.setDescription(model.getDescription());
        entity.setTrxDate(LocalDate.parse(model.getTrxDate()));
        entity.setTrxTime(LocalTime.parse(model.getTrxTime()));
        entity.setCustomerId(model.getCustomerId());
        return entity;
    }
}
