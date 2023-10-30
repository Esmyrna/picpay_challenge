package com.picpaysimplificado;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import com.picpaysimplificado.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testValidateTransactionSufficientBalance() throws Exception {
        User sender = new User();
        sender.setBalance(new BigDecimal("100.00"));
        BigDecimal amount = new BigDecimal("50.00");

        assertDoesNotThrow(() -> userService.validateTransaction(sender, amount));
    }

    @Test
    public void testValidateTransactionInsufficientBalance() {
        User sender = new User();
        sender.setBalance(new BigDecimal("30.00"));
        BigDecimal amount = new BigDecimal("50.00");

        assertThrows(Exception.class, () -> userService.validateTransaction(sender, amount));
    }

    @Test
    public void testValidateTransactionMerchantUser() {
        User sender = new User();
        sender.setUserType(UserType.MERCHANT);
        sender.setBalance(new BigDecimal("1000.00"));
        BigDecimal amount = new BigDecimal("50.00");

        Exception exception = assertThrows(Exception.class, () -> userService.validateTransaction(sender, amount));
        assertEquals("Usuário do tipo logista não está autorizado.", exception.getMessage());
    }

    @Test
    public void testFindUserByIdUserExists() throws Exception {
        User user = new User();
        user.setId(1L);
        Mockito.when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(1L);
        assertNotNull(foundUser);
    }

    @Test
    public void testFindUserByIdUserNotFound() {
        Mockito.when(userRepository.findUserById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> userService.findUserById(2L));
        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO("Esmyrna", "Cavalcanti", "1234456", 10, "esmyrna@gmail.com", "12345", UserType.COMMON);
        User newUser = new User(userDTO);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(newUser);

        User createdUser = userService.createUser(userDTO);
        assertNotNull(createdUser);
    }


    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
    }
}
