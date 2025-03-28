package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for providing administrative operations within the application.
 *
 * The primary functionality of this class is to facilitate administrative-level
 * user authentication by delegating login functionality to the {@code UserService}.
 * This service acts as an intermediary layer to handle administrative actions.
 */
@Service
public class AdminService {
    private final UserService userService;

    public AdminService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Authenticates a user based on the provided email and password credentials.
     * This method delegates the authentication process to the underlying {@code UserService}.
     *
     * @param email The email address of the user attempting to log in.
     * @param password The password associated with the provided email.
     * @return A {@code UUID} representing the unique identifier for the authenticated user.
     *         Returns {@code null} if the authentication fails due to invalid credentials or
     *         if no account is found for the provided email.
     */
    public UUID login(String email, String password) {

        return userService.login(email, password);
    }
}
