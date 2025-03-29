package project.testmaster.backend.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    void loginAdmin_WithInvalidCredentials_ShouldReturnNull() {
        // Act
        UUID userId = adminService.login("nonexistent@example.com", "password123");

        // Assert
        assertNull(userId);
    }

}