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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getPercentFee() {
        return percentFee;
    }

    public void setPercentFee(float percentFee) {
        this.percentFee = percentFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
