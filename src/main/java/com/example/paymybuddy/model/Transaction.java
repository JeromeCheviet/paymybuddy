package com.example.paymybuddy.model;

import javax.persistence.*;
import java.time.LocalDate;


/**
 *  Class use to connect with table contact
 */
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "amount")
    private float amount;

    @Column(name = "percent_fee")
    private float percentFee;

    @Column(name = "description")
    private String description;

    @Column(name = "total_amount")
    private float totalAmount;

}
