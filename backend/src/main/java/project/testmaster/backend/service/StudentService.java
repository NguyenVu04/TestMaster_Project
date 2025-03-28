package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.model.Student;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.StudentRepository;

/**
 * Service class responsible for managing student-related operations.
 */
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private final UserService userService;

    /**
     * Constructs a new StudentService with the specified dependencies.
     *
     * @param studentRepository the repository used to manage student entities
     * @param userService the service used to manage user-related operations
     */
    public StudentService(StudentRepository studentRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    /**
     * Registers a new student by creating a user and saving the corresponding student entity.
     *
     * @param request the signup request containing information such as first name,
     *                last name, phone number, email, and password.
     */
    public void registerStudent(SignupRequestDTO request) {
        User user = userService.createUser(
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getPassword());

        Student student = new Student(user);

        studentRepository.save(student);
    }

    /**
     * Retrieves a student by their unique identifier.
     *
     * @param id the unique identifier of the student to be retrieved
     * @return the student associated with the provided id if found, otherwise null
     */
    public Student getStudentById(UUID id) {
        return studentRepository.findById(id).orElse(null);
    }

    /**
     * Authenticates a user using their email and password.
     *
     * @param email the email address of the user attempting to log in
     * @param password the password associated with the provided email
     * @return the unique identifier (UUID) of the authenticated user,
     *         or null if authentication fails
     */
    public UUID login(String email, String password) {

        return userService.login(email, password);
    }
}