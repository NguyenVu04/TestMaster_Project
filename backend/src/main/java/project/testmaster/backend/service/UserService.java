package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.UserRepository;

/**
 * Service class responsible for user management, including creation, retrieval,
 * update, deletion of users, and user authentication through login functionality.
 * This service interacts with the UserRepository for user data persistence
 * and relies on the AccountService to manage associated account operations.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    private final AccountService accountService;

    /**
     * Constructs an instance of UserService with the specified UserRepository and AccountService.
     *
     * @param userRepository the repository instance for managing User entities
     * @param accountService the service instance for managing associated Account operations
     */
    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user to be retrieved
     * @return the User object if found, or null if no user exists with the given id
     */
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Creates a new user in the system and associates an account with it.
     * The method initializes a User object with the specified personal details,
     * persists it using the UserRepository, and creates an associated account
     * using the provided email and password.
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param phoneNumber the phone number of the user
     * @param email the email address for the user's account
     * @param password the plain text password for the user's account
     * @return the User object that was created and saved
     */
    public User createUser(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String password
    ) {
        User user = new User(firstName, lastName, phoneNumber);
        userRepository.save(user);
        accountService.createAccount(user, email, password);
        return user;
    }

    /**
     * Deletes the user identified by the given unique identifier.
     *
     * @param id the unique identifier of the user to be deleted
     */
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    /**
     * Updates an existing user in the repository with the provided user details.
     * The method persists the updated user information in the data store.
     *
     * @param user The user object containing updated details to be saved.
     * @return The user object after being saved in the repository.
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Authenticates a user by checking the provided email and password against stored data.
     *
     * @param email    the email address of the user attempting to log in
     * @param password the plain text password provided for authentication
     * @return the UUID of the authenticated user if the email and password match;
     *         null if authentication fails
     */
    public UUID login(String email, String password) {
        Account account = accountService.getAccountByEmail(email);

        if (account == null) {
            return null;
        }

        if (!accountService.checkPassword(account, password)) {
            return null;
        }

        return account.getUserId();
    }
}
