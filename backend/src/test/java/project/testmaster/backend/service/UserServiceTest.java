package project.testmaster.backend.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.AccountRepository;
import project.testmaster.backend.repository.UserRepository;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    private User testUser;
    private Account testAccount;

    @BeforeEach
    void setUp() {
        testUser = userService.createUser(
                "test",
                "test",
                "0123456789",
                "test@gmail.com",
                "password123");
        testAccount = accountService.getAccountByEmail("test_create@gmail.com");
    }

    @Test
    void createUser_ShouldCreateUserAndAccount() {
        // Arrange
        String email = "test_create@gmail.com";

        // Act
        User user = userService.createUser(
                "Test",
                "User",
                "0987654321",
                email,
                "password123");

        // Assert
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("0987654321", user.getPhoneNumber());

        // Verify account was created
        Account account = accountService.getAccountByEmail(email);
        assertNotNull(account);
        assertEquals(user.getId(), account.getUserId());
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {

        // Act
        String testId = "11111111-1111-1111-1111-111111111111";
        UUID userId = UUID.fromString(testId);
        User foundUser = userService.getUserById(userId);

        // Assert
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());

    }

    @Test
    void getUserById_WhenUserNotExists_ShouldReturnNull() {
        // Act
        User foundUser = userService.getUserById(UUID.randomUUID());

        // Assert
        assertNull(foundUser);
    }

    @Test
    void updateUser_ShouldUpdateUserInformation() {
        // Arrange
        String testId = "11111111-1111-1111-1111-111111111111";
        UUID userId = UUID.fromString(testId);
        User testUser = userService.getUserById(userId);
        testUser.setFirstName("updated");
        testUser.setLastName("user");

        // Act
        User updatedUser = userService.updateUser(testUser);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("updated", updatedUser.getFirstName());
        assertEquals("user", updatedUser.getLastName());

        // Verify changes in database
        User foundUser = userService.getUserById(testUser.getId());
        assertEquals("updated", foundUser.getFirstName());
        assertEquals("user", foundUser.getLastName());
    }

    @Test
    void deleteUser_ShouldRemoveUserFromDatabase() {
        // Arrange
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        // Act
        userService.deleteUser(userId);

        // Assert
        assertNull(userService.getUserById(userId));
    }

    @Test
    void login_WithValidCredentials_ShouldReturnUserId() {
        // Act
        String email = "test@gmail.com";
        String password = "password123";
        UUID userId = userService.login(email, password);

        // Assert
        assertNotNull(userId);
        assertEquals(testUser.getId(), userId);
    }

    @Test
    void login_WithInvalidEmail_ShouldReturnNull() {
        // Act
        UUID userId = userService.login("wrong@example.com", "wrong_password");

        // Assert
        assertNull(userId);
    }

}