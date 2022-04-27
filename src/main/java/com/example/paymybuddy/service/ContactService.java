package com.example.paymybuddy.service;

import com.example.paymybuddy.model.Contact;
import com.example.paymybuddy.repository.ContactRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class to interact with contact table data.
 */
@Service
public class ContactService {

    private static final Logger logger = LogManager.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    /**
     * Method which returns all data from contact table
     *
     * @return <b>Contact</b> : All data in Contact object.
     */
    public Iterable<Contact> getContacts() {
        logger.debug("Get all contacts.");
        return contactRepository.findAll();
    }
}
