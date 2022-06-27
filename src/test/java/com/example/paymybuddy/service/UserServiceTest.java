package com.example.paymybuddy.service;

import com.example.paymybuddy.model.application.EditUserForm;
import com.example.paymybuddy.model.application.SignupForm;
import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
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

    }

    @Test
    void testGetUsers() {
        List<User> expectUserList = new ArrayList<>();
        expectUserList.add(expectedUser);

        when(userRepository.findAll()).thenReturn(expectUserList);

        Iterable<User> actualUser = userService.getUsers();

        assertEquals(expectUserList, actualUser);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(expectedUser));

        Optional<User> actualUser = userService.getUserById(1);

        assertEquals(expectedUser.getEmail(), actualUser.get().getEmail());
        verify(userRepository, times(1)).findById(1);

    }

    @Test
    void testGetUserByEmail() {
        String actualEmail = "jerome@mail.fr";

        when(userRepository.findByEmail("jerome@mail.fr")).thenReturn(expectedUser);

        User actualUser = userService.getUserByEmail(actualEmail);


        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findByEmail("jerome@mail.fr");

    }

    @Test
    void testTransferFromBank() {
        expectAmount = 5.0f;
        expectedUser.setBalance(expectAmount);
        expectTransferType = "credit";

        User actualUser = new User();
        actualUser.setUserId(1);
        actualUser.setEmail("jerome@mail.fr");
        actualUser.setUserName("jerome");
        actualUser.setRib("000-111");
        actualUser.setBankName("mabank");
        actualUser.setBalance(0.0f);

        when(userRepository.save(actualUser)).thenReturn(actualUser);

        userService.transferFromOrToBank(actualUser, expectAmount, expectTransferType);

        assertEquals(expectedUser.getBalance(), actualUser.getBalance());
        verify(userRepository, times(1)).save(actualUser);

    }

    @Test
    void testTransferToBank() {
        expectAmount = 2.0f;
        expectedUser.setBalance(3.0f);
        expectTransferType = "debit";

        User actualUser = new User();
        actualUser.setUserId(1);
        actualUser.setEmail("jerome@mail.fr");
        actualUser.setUserName("jerome");
        actualUser.setRib("000-111");
        actualUser.setBankName("mabank");
        actualUser.setBalance(5.0f);

        when(userRepository.save(actualUser)).thenReturn(actualUser);

        userService.transferFromOrToBank(actualUser, expectAmount, expectTransferType);

        assertEquals(expectedUser.getBalance(), actualUser.getBalance());
        verify(userRepository, times(1)).save(actualUser);

    }

    @Test
    void testTransferToBank_thenReturnZero_whenBalanceEqualZero() {
        expectAmount = 5.0f;
        expectTransferType = "debit";

        User actualUser = new User();
        actualUser.setUserId(1);
        actualUser.setEmail("jerome@mail.fr");
        actualUser.setUserName("jerome");
        actualUser.setRib("000-111");
        actualUser.setBankName("mabank");
        actualUser.setBalance(0.0f);

        when(userRepository.save(actualUser)).thenReturn(actualUser);

        userService.transferFromOrToBank(actualUser, expectAmount, expectTransferType);

        assertEquals(expectedUser.getBalance(), actualUser.getBalance());
        verify(userRepository, times(1)).save(actualUser);

    }

    @Test
    void testChangeUserRole_thenChangeRoleToTrue_whenUserRoleIsFalse() {
        User actualUser = new User();
        actualUser.setUserId(1);
        actualUser.setEmail("jerome@mail.fr");
        actualUser.setUserName("jerome");
        actualUser.setRib("000-111");
        actualUser.setBankName("mabank");
        actualUser.setBalance(0.0f);
        actualUser.setRole(false);

        when(userRepository.save(actualUser)).thenReturn(actualUser);

        userService.changeUserRole(actualUser);

        assertEquals(expectedUser.isRole(), actualUser.isRole());
        verify(userRepository, times(1)).save(actualUser);
    }

    @Test
    void testChangeUserRole_thenChangeRoleToFalse_whenUserRoleIsTrue() {
        expectedUser.setRole(false);

        User actualUser = new User();
        actualUser.setUserId(1);
        actualUser.setEmail("jerome@mail.fr");
        actualUser.setUserName("jerome");
        actualUser.setRib("000-111");
        actualUser.setBankName("mabank");
        actualUser.setBalance(0.0f);
        actualUser.setRole(true);

        when(userRepository.save(actualUser)).thenReturn(actualUser);

        userService.changeUserRole(actualUser);

        assertEquals(expectedUser.isRole(), actualUser.isRole());
        verify(userRepository, times(1)).save(actualUser);
    }

    @Test
    void testSaveUser_returnTrue() {
        SignupForm newUser = new SignupForm();
        newUser.setEmail("john@mail.fr");
        newUser.setPassword("123");
        newUser.setUserName("john");
        newUser.setBankName("my bank");
        newUser.setRib("FR0700001111222");

        when(userRepository.findByEmail("john@mail.fr")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        boolean actualState = userService.saveUser(newUser);

        assertEquals(true, actualState);
        verify(userRepository, times(1)).findByEmail("john@mail.fr");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSaveUser_returnFalse() {
        SignupForm newUser = new SignupForm();
        newUser.setEmail(expectedUser.getEmail());
        newUser.setPassword(expectedUser.getPassword());
        newUser.setUserName(expectedUser.getUserName());
        newUser.setBankName(expectedUser.getBankName());
        newUser.setRib(expectedUser.getRib());
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(expectedUser);

        boolean actualState = userService.saveUser(newUser);

        assertEquals(false, actualState);
        verify(userRepository, times(1)).findByEmail(expectedUser.getEmail());
    }

    @Test
    void testModifyUser_modifyUserName() {
       EditUserForm modifyUser = new EditUserForm();
        modifyUser.setUserName("toto");
        modifyUser.setPassword("123");
        modifyUser.setRib("000-111");
        modifyUser.setBankName("mabank");

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.modifyUser(expectedUser, modifyUser);

        assertEquals(expectedUser.getUserName(), modifyUser.getUserName());
        verify(userRepository, times(1)).save(expectedUser);

    }

    @Test
    void testModifyUser_modifyPassword() {
        EditUserForm modifyUser = new EditUserForm();
        modifyUser.setUserName("jerome");
        modifyUser.setPassword("456");
        modifyUser.setRib("000-111");
        modifyUser.setBankName("mabank");

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.modifyUser(expectedUser, modifyUser);

        assertEquals(expectedUser.getPassword(), modifyUser.getPassword());
        verify(userRepository, times(1)).save(expectedUser);

    }

    @Test
    void testModifyUser_modifyRib() {
        EditUserForm modifyUser = new EditUserForm();
        modifyUser.setUserName("jerome");
        modifyUser.setPassword("123");
        modifyUser.setRib("000-222");
        modifyUser.setBankName("mabank");

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.modifyUser(expectedUser, modifyUser);

        assertEquals(expectedUser.getRib(), modifyUser.getRib());
        verify(userRepository, times(1)).save(expectedUser);

    }

    @Test
    void testModifyUser_modifyBankName() {
        EditUserForm modifyUser = new EditUserForm();
        modifyUser.setUserName("jerome");
        modifyUser.setPassword("123");
        modifyUser.setRib("000-111");
        modifyUser.setBankName("tabank");

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.modifyUser(expectedUser, modifyUser);

        assertEquals(expectedUser.getBankName(), modifyUser.getBankName());
        verify(userRepository, times(1)).save(expectedUser);

    }

    @Test
    void testModifyUser_modifyAll() {
        EditUserForm modifyUser = new EditUserForm();
        modifyUser.setUserName("toto");
        modifyUser.setPassword("456");
        modifyUser.setRib("000-222");
        modifyUser.setBankName("tabank");

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.modifyUser(expectedUser, modifyUser);

        assertEquals(expectedUser.getUserName(), modifyUser.getUserName());
        assertEquals(expectedUser.getPassword(), modifyUser.getPassword());
        assertEquals(expectedUser.getRib(), modifyUser.getRib());
        assertEquals(expectedUser.getBankName(), modifyUser.getBankName());
        verify(userRepository, times(1)).save(expectedUser);

    }

    @Test
    void testAddContact_withNoContactList() {
        User newContact = new User();
        newContact.setUserId(1000);
        newContact.setEmail("toto@mail.fr");
        newContact.setUserName("toto");
        newContact.setPassword("456");
        newContact.setRib("000-222");
        newContact.setBankName("sabank");

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.addContact(expectedUser, newContact);

        assertThat(expectedUser.getFriendList())
                .hasSize(1)
                .extracting(User::getEmail)
                .contains("toto@mail.fr");
        verify(userRepository, times(1)).save(expectedUser);
    }

    @Test
    void testAddContact_withExistingContactList() {
        User existingContact = new User();
        existingContact.setUserId(1000);
        existingContact.setEmail("toto@mail.fr");
        existingContact.setUserName("toto");
        existingContact.setPassword("456");
        existingContact.setRib("000-222");
        existingContact.setBankName("sabank");

        User newContact = new User();
        newContact.setUserId(1100);
        newContact.setEmail("tata@mail.fr");
        newContact.setUserName("tata");
        newContact.setPassword("456");
        newContact.setRib("000-222");
        newContact.setBankName("tabank");

        List<User> contactList = new ArrayList<>();
        contactList.add(existingContact);

        expectedUser.setFriendList(contactList);

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.addContact(expectedUser, newContact);

        assertThat(expectedUser.getFriendList())
                .hasSize(2)
                .extracting(User::getEmail)
                .contains("tata@mail.fr");
        verify(userRepository, times(1)).save(expectedUser);

    }

    @Test
    void testAddContact_thenNoAddContact_whenContactAlreadyExist() {
        User existingContact = new User();
        existingContact.setUserId(1000);
        existingContact.setEmail("toto@mail.fr");
        existingContact.setUserName("toto");
        existingContact.setPassword("456");
        existingContact.setRib("000-222");
        existingContact.setBankName("sabank");

        List<User> contactList = new ArrayList<>();
        contactList.add(existingContact);

        expectedUser.setFriendList(contactList);

        userService.addContact(expectedUser, existingContact);

        assertThat(expectedUser.getFriendList())
                .hasSize(1)
                .extracting(User::getEmail)
                .contains("toto@mail.fr");
    }

    @Test
    void testRemoveContact() {
        User existingContact = new User();
        existingContact.setUserId(1000);
        existingContact.setEmail("toto@mail.fr");
        existingContact.setUserName("toto");
        existingContact.setPassword("456");
        existingContact.setRib("000-222");
        existingContact.setBankName("sabank");

        List<User> contactList = new ArrayList<>();
        contactList.add(existingContact);
        expectedUser.setFriendList(contactList);

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.removeContact(expectedUser, existingContact);

        assertThat(expectedUser.getFriendList())
                .isEmpty();
        verify(userRepository, times(1)).save(expectedUser);

    }

    @Test
    void testRemoveContact_withUnknownContact() {
        User existingContact = new User();
        existingContact.setUserId(1000);
        existingContact.setEmail("toto@mail.fr");
        existingContact.setUserName("toto");
        existingContact.setPassword("456");
        existingContact.setRib("000-222");
        existingContact.setBankName("sabank");

        User unknownContact = new User();
        unknownContact.setUserId(1100);
        unknownContact.setEmail("tata@mail.fr");
        unknownContact.setUserName("tata");
        unknownContact.setPassword("456");
        unknownContact.setRib("000-222");
        unknownContact.setBankName("tabank");


        List<User> contactList = new ArrayList<>();
        contactList.add(existingContact);
        expectedUser.setFriendList(contactList);

        userService.removeContact(expectedUser, unknownContact);

        assertThat(expectedUser.getFriendList())
                .hasSize(1)
                .extracting(User::getEmail)
                .contains("toto@mail.fr");

    }

    @Test
    void testMakeTransaction() {
        List<User> userList = new ArrayList<>();

        User expectedBeneficiaryUser = new User();
        expectedBeneficiaryUser.setUserId(1100);
        expectedBeneficiaryUser.setEmail("tata@mail.fr");
        expectedBeneficiaryUser.setUserName("tata");
        expectedBeneficiaryUser.setPassword("456");
        expectedBeneficiaryUser.setRib("000-222");
        expectedBeneficiaryUser.setBankName("tabank");

        expectedUser.setBalance(10.0f);

        userList.add(expectedUser);
        userList.add(expectedBeneficiaryUser);

        when(userRepository.saveAll(userList)).thenReturn(userList);

        userService.makeTransaction(5.0f, expectedUser, expectedBeneficiaryUser);

        assertEquals(5.0f, expectedUser.getBalance());
        assertEquals(5.0f, expectedBeneficiaryUser.getBalance());
        verify(userRepository, times(1)).saveAll(userList);
    }

    @Test
    void testUserDelete() {
        doNothing().when(userRepository).delete(expectedUser);
        userService.userDelete(expectedUser);

        verify(userRepository, times(1)).delete(expectedUser);
    }

    @Test
    void testGetUserByPage() {
        List<User> userList = new ArrayList<>();
        userList.add(expectedUser);
        Page<User> expectedPage = new PageImpl(userList);

        when(userRepository.findAll(PageRequest.of(0,3))).thenReturn(expectedPage);

        Page<User> actualPage = userService.getUsersByPage(PageRequest.of(0,3));

        assertEquals(expectedPage, actualPage);
        verify(userRepository, times(1)).findAll(PageRequest.of(0,3));
    }

}
