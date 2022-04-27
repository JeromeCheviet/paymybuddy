package com.example.paymybuddy.model;

import javax.persistence.*;


/**
 *  Class use to connect with table contact
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "rib")
    private String rib;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "transfert_to_bank")
    private float transfertToBank;

    @Column(name = "transfert_from_bank")
    private float transfertFromBank;

    @Column(name = "balance")
    private float balance;

    @Column(name = "role")
    private boolean role;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public float getTransfertToBank() {
        return transfertToBank;
    }

    public void setTransfertToBank(float transfertToBank) {
        this.transfertToBank = transfertToBank;
    }

    public float getTransfertFromBank() {
        return transfertFromBank;
    }

    public void setTransfertFromBank(float transfertFromBank) {
        this.transfertFromBank = transfertFromBank;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public boolean getRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }
}
