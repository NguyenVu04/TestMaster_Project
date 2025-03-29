package project.testmaster.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.AccountRepository;

/**
 * Service class responsible for business logic related to Account operations.
 * This service interacts with the AccountRepository to handle persistence
 * and utilizes PasswordEncoder for password management.
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an instance of AccountService with the specified AccountRepository and PasswordEncoder.
     *
     * @param accountRepository the repository instance for managing Account entities
     * @param passwordEncoder the password encoder for encoding and verifying passwords
     */
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new account and saves it in the repository.
     *
     * @param user     the user associated with the new account
     * @param email    the email address of the new account
     * @param password the plain text password of the new account, which will be encoded before saving
     */
    public void createAccount(User user, String email, String password) {
        Account account = new Account(
                user,
                email,
                passwordEncoder
                        .encode(password));
        accountRepository.save(account);
    }

    /**
     * Retrieves an account associated with the specified email address.
     *
     * @param email the email address to search for
     * @return the account associated with the given email if found; otherwise, null
     */
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElse(null);
    }

    /**
     * Verifies whether the provided password matches the stored password of the given account.
     *
     * @param account  the account whose stored password is to be compared
     * @param password the plain text password provided for verification
     * @return true if the provided password matches the stored password; false otherwise
     */
    public boolean checkPassword(Account account, String password) {
        return passwordEncoder.matches(password, account.getPassword());
    }
}
