package com.example.paymybuddy.service;

import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    Float expectAmount;
    String expectTransferType;

    @InjectMocks
    private UserService userService = new UserServiceImpl();
    @Mock
    private UserRepository userRepository;
    @Mock
    private User expectedUser;


    @BeforeEach
    private void setUp() {
        int expectedUserId = 1;
        String expectedEmail = "jerome@mail.fr";
        String expectedName = "jerome";
        String expectedRib = "000-111";
        String expectedBankName = "mabank";
        float expectedTransfertToBank = 0.0f;
        float expectedTransfertFromBank = 0.0f;
        float expectedBalance = 0.0f;


        expectedUser = new User();
        expectedUser.setUserId(expectedUserId);
        expectedUser.setEmail(expectedEmail);
        expectedUser.setUserName(expectedName);
        expectedUser.setRib(expectedRib);
        expectedUser.setBankName(expectedBankName);
        expectedUser.setTransfertToBank(expectedTransfertToBank);
        expectedUser.setTransfertFromBank(expectedTransfertFromBank);
        expectedUser.setBalance(expectedBalance);
        expectedUser.isRole();

    }

    @Test
    public void testGetUsers() {
        List<User> expectUserList = new ArrayList<>();
        expectUserList.add(expectedUser);

        when(userRepository.findAll()).thenReturn(expectUserList);

        Iterable<User> actualUser = userService.getUsers();

        assertEquals(expectUserList, actualUser);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserByEmail() {
        String actualEmail = "jerome@mail.fr";

        when(userRepository.findByEmail("jerome@mail.fr")).thenReturn(expectedUser);

        User actualUser = userService.getUserByEmail(actualEmail);


        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findByEmail("jerome@mail.fr");

    }
/*
    @Test
    public void TestTransferFromOrToBank_shouldReturnTrue_whenTransferFromBank() {
        expectAmount = 5.0f;
        expectedUser.setBalance(expectAmount);
        expectTransferType = "credit";

        User actualUser = new User();
        actualUser.setUserId(1);
        actualUser.setEmail("jerome@mail.fr");
        actualUser.setName("jerome");
        actualUser.setRib("000-111");
        actualUser.setBankName("mabank");
        actualUser.setTransfertToBank(0.0f);
        actualUser.setTransfertFromBank(0.0f);
        actualUser.setBalance(0.0f);
        actualUser.isRole();

        when(userRepository.save(actualUser)).thenReturn(expectedUser);


    }*/
}
