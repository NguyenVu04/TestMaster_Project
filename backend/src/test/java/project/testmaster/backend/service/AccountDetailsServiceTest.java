package project.testmaster.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.AccountRepository;

@SpringBootTest
@Transactional
class AccountDetailsServiceTest {

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        // Create test user and account
        userService.createUser(
                "Test",
                "User",
                "0123456789",
                "test@example.com",
                "password123");
        testAccount = accountRepository.findByEmail("test@example.com").get();
    }

    @Test
    void loadUserByUsername_WhenUserExists_ShouldReturnUserDetails() {
        // Act
        UserDetails userDetails = accountDetailsService.loadUserByUsername(
                testAccount.getUserId().toString());

        // Assert
        assertNotNull(userDetails);
        assertEquals(testAccount.getUsername(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_WhenUserNotFound_ShouldThrowException() {
        // Arrange
        String nonExistentId = UUID.randomUUID().toString();

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            accountDetailsService.loadUserByUsername(nonExistentId);
        });
    }

    @Test
    void loadUserByUsername_WhenInvalidUUID_ShouldThrowException() {
        // Arrange
        String invalidId = "invalid-uuid";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accountDetailsService.loadUserByUsername(invalidId);
        });
    }
}