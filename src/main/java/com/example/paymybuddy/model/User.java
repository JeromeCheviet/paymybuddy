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

    @Column(name = "transfer_from_bank")
    private float transfertFromBank;

    @Column(name = "balance")
    private float balance;

}
