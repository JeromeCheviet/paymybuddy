package com.example.paymybuddy.model.dto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(
            mappedBy = "contacts"
    )
    private List<User> users = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
