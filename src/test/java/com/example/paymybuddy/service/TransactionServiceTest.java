package com.example.paymybuddy.service;

import com.example.paymybuddy.model.application.AddTransferForm;
import com.example.paymybuddy.model.dto.Transaction;
import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.repository.TransactionRepository;
import com.example.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService = new TransactionServiceImpl();

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private Transaction expectedTransaction;

    @Mock
    private User expectedUser;

    @Mock
    private User beneficiaryUser;

    @BeforeEach
    private void setUp() {
        int expectedUserId = 1;
        String expectedEmail = "jerome@mail.fr";
        String expectedName = "jerome";
        String expectedRib = "000-111";
        String expectedBankName = "mabank";
        float expectedBalance = 0.0f;

        expectedUser = new User();
        expectedUser.setUserId(expectedUserId);
        expectedUser.setEmail(expectedEmail);
        expectedUser.setPassword("123");
        expectedUser.setUserName(expectedName);
        expectedUser.setRib(expectedRib);
        expectedUser.setBankName(expectedBankName);
        expectedUser.setBalance(expectedBalance);
        expectedUser.setRole(true);

        beneficiaryUser = new User();
        beneficiaryUser.setUserId(2000);
        beneficiaryUser.setEmail("john@mail.fr");
        beneficiaryUser.setUserName("john");
        beneficiaryUser.setRib("000-111");
        beneficiaryUser.setBankName("mabank");
        beneficiaryUser.setBalance(0.0f);


        expectedTransaction = new Transaction();
        expectedTransaction.setId(3);
        expectedTransaction.setDate(LocalDate.now());
        expectedTransaction.setUser(expectedUser);
        expectedTransaction.setBeneficiaryUser(beneficiaryUser);
        expectedTransaction.setDescription("Is a test");
        expectedTransaction.setTransactionType("credit");
        expectedTransaction.setAmount(0.0f);
        expectedTransaction.setPercentFee(0.5f);
        expectedTransaction.setTotalAmount(0.05f);


    }

    @Test
    void testGetTransactions() {
        List<Transaction> expectedTransactionList = new ArrayList<>();
        expectedTransactionList.add(expectedTransaction);

        when(transactionRepository.findAll()).thenReturn(expectedTransactionList);

        Iterable<Transaction> actualTransaction = transactionService.getTransactions();

        assertEquals(expectedTransactionList, actualTransaction);
        verify(transactionRepository, times(1)).findAll();

    }

    @Test
    void testAddTransaction() {
        AddTransferForm addTransferForm = new AddTransferForm();

        addTransferForm.setUserId(2000);
        addTransferForm.setConnection("John");
        addTransferForm.setDescription("Is a test");
        addTransferForm.setAmount(10.0f);

        when(userService.getUserById(2000)).thenReturn(Optional.of(beneficiaryUser));
        doNothing().when(userService).makeTransaction(10.0f, expectedUser, beneficiaryUser);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);

        transactionService.addTransaction(expectedUser, addTransferForm);

        assertEquals(0.05f, expectedTransaction.getTotalAmount());
        verify(userService, times(1)).getUserById(2000);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}
