package com.customerdata.batchprocess.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "transaction")
@Entity
@Data
public class Transaction {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private Double trxAmount;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate trxDate;
    @Column(nullable = false)
    private LocalTime trxTime;
    @Column(nullable = false)
    private String customerId;

    @Version
    @Column(nullable = false)
    private Integer version;
}
