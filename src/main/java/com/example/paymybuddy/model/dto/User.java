package com.example.paymybuddy.model.dto;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 *  Class use to connect with table contact
 */
@Entity
@DynamicUpdate
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
    private String userName;

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

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id")
    List<Transaction> transactions = new ArrayList<>();

    /*
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "beneficiary_user_id")
    List<Transaction> beneficiaries = new ArrayList<>();
    */

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "assoc_user_contact",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<Contact> contacts = new ArrayList<>();

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /*
    public List<Transaction> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<Transaction> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }
    */

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

}
