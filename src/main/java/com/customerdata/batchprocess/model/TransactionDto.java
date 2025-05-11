package com.customerdata.batchprocess.model;

import lombok.Data;

@Data
public class TransactionDto {

    private Long id;
    private String accountNumber;
    private Double trxAmount;
    private String description;
    private String trxDate;
    private String trxTime;
    private String customerId;
}
