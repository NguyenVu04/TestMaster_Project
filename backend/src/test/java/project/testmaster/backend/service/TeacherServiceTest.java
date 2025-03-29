package project.testmaster.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.AccountRepository;
import project.testmaster.backend.repository.TeacherRepository;
import project.testmaster.backend.repository.UserRepository;

@SpringBootTest
@Transactional
class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void registerTeacher_ShouldCreateUserAndTeacher() {
        // Arrange
        SignupRequestDTO request = new SignupRequestDTO(
                "test.teacher@example.com",
                "password123",
                "Test",
                "Teacher",
                "0123456789");

        // Act
        teacherService.registerTeacher(request);

        // Assert
        // Verify user was created
        Account account = accountRepository.findByEmail(request.getEmail()).orElse(null);
        assertNotNull(account);
        User user = userRepository.findById(account.getUserId()).orElse(null);
        assertNotNull(user);
        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getPhoneNumber(), user.getPhoneNumber());

        // Verify teacher was created
        Teacher teacher = teacherRepository.findById(user.getId()).orElse(null);
        assertNotNull(teacher);
        assertEquals(user.getId(), teacher.getUser().getId());
    }

    @Test
    void getTeacherById_WhenTeacherExists_ShouldReturnTeacher() {
        // Arrange
        SignupRequestDTO request = new SignupRequestDTO(
                "test.teacher@example.com",
                "password123",
                "Test",
                "Teacher",
                "0123456789");

        teacherService.registerTeacher(request);

        Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow();
        Teacher expectedTeacher = teacherRepository.findById(account.getUserId()).orElseThrow();

        // Act
        Teacher result = teacherService.getTeacherById(expectedTeacher.getUserId());

        // Assert
        assertNotNull(result);
        assertEquals(expectedTeacher.getUserId(), result.getUserId());
        assertEquals(expectedTeacher.getUser().getId(), result.getUser().getId());
    }

    @Test
    void getTeacherById_WhenTeacherNotExists_ShouldReturnNull() {
        // Act
        Teacher result = teacherService.getTeacherById(UUID.randomUUID());

        // Assert
        assertNull(result);
    }

    @Test
    void loginTeacher_WithValidCredentials_ShouldReturnUserId() {
        // Arrange
        SignupRequestDTO request = new SignupRequestDTO(
                "test.teacher@example.com",
                "password123",
                "Test",
                "Teacher",
                "0123456789");

        teacherService.registerTeacher(request);

        // Act
        UUID userId = teacherService.login(request.getEmail(),
                request.getPassword());

        // Assert
        assertNotNull(userId);
        Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow();
        assertEquals(account.getUserId(), userId);
    }

    @Test
    void loginTeacher_WithInvalidEmail_ShouldReturnNull() {
        // Act
        UUID userId = teacherService.login("nonexistent@example.com", "password123");

        // Assert
        assertNull(userId);
    }

    @Test
    void loginTeacher_WithInvalidPassword_ShouldReturnNull() {

        // Act
        UUID userId = teacherService.login("test.teacher@example.com", "wrongpassword");

        // Assert
        assertNull(userId);
    }
}