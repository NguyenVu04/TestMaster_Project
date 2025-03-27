package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.TeacherRepository;

/**
 * Service class for managing Teacher-related functionality.
 * This class acts as a bridge between the controller layer and the repository layer,
 * providing methods for teacher registration, retrieval, and login operations.
 */
@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private UserService userService;

    /**
     * Registers a new teacher based on the provided signup request data.
     * The method creates a new User entity and associates it with a Teacher entity,
     * then saves the Teacher entity to the database.
     *
     * @param request the signup request containing the teacher's details, such as first name,
     *                last name, phone number, email, and password
     * @return the registered Teacher entity saved in the database
     */
    public Teacher registerTeacher(SignupRequestDTO request) {
        User user = userService.createUser(
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getPassword());

        Teacher teacher = new Teacher(user);

        return teacherRepository.save(teacher);
    }

    /**
     * Retrieves a Teacher entity by its unique identifier.
     *
     * @param id the unique identifier of the Teacher to retrieve
     * @return the Teacher entity associated with the given UUID, or null if no such entity exists
     */
    public Teacher getTeacherById(UUID id) {
        return teacherRepository.findById(id).orElse(null);
    }

    /**
     * Handles the login operation for a user by validating the provided email and password
     * against stored credentials and returning the user's unique identifier upon successful authentication.
     *
     * @param email the email address of the user attempting to log in
     * @param password the password provided by the user for authentication
     * @return the unique identifier (UUID) of the authenticated user if the login is successful;
     *         returns null if authentication fails
     */
    public UUID login(String email, String password) {
        UUID id = userService.login(email, password);

        return id;
    }
}