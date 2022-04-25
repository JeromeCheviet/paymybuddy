package com.example.paymybuddy.repository;

import com.example.paymybuddy.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries run on the contact table.
 */
@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {
}
