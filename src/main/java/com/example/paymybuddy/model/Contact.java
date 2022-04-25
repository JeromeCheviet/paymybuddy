package com.example.paymybuddy.model;

import javax.persistence.*;

/**
 *  Class use to connect with table contact
 */
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "friend_email")
    private String friendEmail;

}
