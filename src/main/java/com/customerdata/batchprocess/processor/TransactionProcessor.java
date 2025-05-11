package com.customerdata.batchprocess.processor;

import com.customerdata.batchprocess.model.TransactionDto;
import com.customerdata.batchprocess.repository.TransactionRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionProcessor implements ItemProcessor<TransactionDto, TransactionDto> {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionDto process(TransactionDto model) throws Exception {
        int count = transactionRepository.countByAccountNumberAndCustomerIdAndTrxDateAndTrxTimeAndTrxAmount(
                model.getAccountNumber(),
                model.getCustomerId(),
                LocalDate.parse(model.getTrxDate()),
                LocalTime.parse(model.getTrxTime()),
                model.getTrxAmount());

        return count == 0 ? model : null;
    }
}