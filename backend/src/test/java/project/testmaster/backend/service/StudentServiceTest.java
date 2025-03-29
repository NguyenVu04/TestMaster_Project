package project.testmaster.backend.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.Student;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.AccountRepository;
import project.testmaster.backend.repository.StudentRepository;
import project.testmaster.backend.repository.UserRepository;

@SpringBootTest
@Transactional
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void registerStudent_ShouldCreateUserAndStudent() {
        // Arrange
        SignupRequestDTO request = new SignupRequestDTO(
                "test.student@example.com",
                "password123",
                "Test",
                "Student",
                "0123456789");

        // Act
        studentService.registerStudent(request);

        // Assert
        // Verify user was created
        Account account = accountRepository.findByEmail(request.getEmail()).orElse(null);
        assertNotNull(account);
        User user = userRepository.findById(account.getUserId()).orElse(null);
        assertNotNull(user);
        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getPhoneNumber(), user.getPhoneNumber());

        // Verify student was created
        Student student = studentRepository.findById(user.getId()).orElse(null);
        assertNotNull(student);
        assertEquals(user.getId(), student.getUser().getId());
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() {
        // Arrange
        SignupRequestDTO request = new SignupRequestDTO(
                "test.student@example.com",
                "password123",
                "Test",
                "Student",
                "0123456789");

        studentService.registerStudent(request);

        Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow();
        Student expectedStudent = studentRepository.findById(account.getUserId()).orElseThrow();

        // Act
        Student result = studentService.getStudentById(expectedStudent.getUserId());

        // Assert
        assertNotNull(result);
        assertEquals(expectedStudent.getUserId(), result.getUserId());
        assertEquals(expectedStudent.getUser().getId(), result.getUser().getId());
    }

    @Test
    void getStudentById_WhenStudentNotExists_ShouldReturnNull() {
        // Act
        Student result = studentService.getStudentById(UUID.randomUUID());

        // Assert
        assertNull(result);
    }

    @Test
    void login_WithValidCredentials_ShouldReturnUserId() {
        // Arrange
        SignupRequestDTO request = new SignupRequestDTO(
                "test.student@example.com",
                "password123",
                "Test",
                "Student",
                "0123456789");

        studentService.registerStudent(request);

        // Act
        UUID userId = studentService.login(request.getEmail(),
                request.getPassword());

        // Assert
        assertNotNull(userId);
        Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow();
        assertEquals(account.getUserId(), userId);
    }

    @Test
    void login_WithInvalidEmail_ShouldReturnNull() {
        // Act
        UUID userId = studentService.login("nonexistent@example.com", "password123");

        // Assert
        assertNull(userId);
    }

    @Test
    void login_WithInvalidPassword_ShouldReturnNull() {

        // Act
        UUID userId = studentService.login("test.student@example.com", "wrongpassword");

        // Assert
        assertNull(userId);
    }
}