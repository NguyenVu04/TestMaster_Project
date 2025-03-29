package project.testmaster.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;
import project.testmaster.backend.model.Account;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @Test
    void getAccountByEmail_NotExists() {
        String email = "notexist@gmail.com";
        Account result = accountService.getAccountByEmail(email);

        assertNull(result);
    }

    @Test
    void getAccountByEmail_WhenAccountExists_ShouldReturnAccount() {
        String email = "kudo@gmail.com";
        Account result = accountService.getAccountByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void checkPassword_WhenPasswordMatches_ShouldReturnTrue() {
        // Arrange
        Account account = new Account();
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);

        // Act
        boolean result = accountService.checkPassword(account, rawPassword);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkPassword_WhenPasswordDoesNotMatch_ShouldReturnFalse() {
        // Arrange
        Account account = new Account();
        account.setPassword("{bcrypt}not_encodedpassword");
        String rawPassword = "password123";

        // Act
        boolean result = accountService.checkPassword(account, rawPassword);

        // Assert
        assertFalse(result);
    }
}
