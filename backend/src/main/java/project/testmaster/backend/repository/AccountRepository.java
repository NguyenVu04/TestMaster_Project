package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.testmaster.backend.model.Account;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for performing database operations on {@link Account} entities.
 * This interface extends {@link JpaRepository}, providing CRUD operations and additional
 * database query capabilities.
 *
 * The repository performs operations using {@link UUID} as the primary key type.
 *
 * It also includes a custom method to find an {@link Account} by its email.
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {
    /**
     * Finds an account by its email address.
     *
     * @param email the email address of the account to retrieve
     * @return an {@link Optional} containing the found {@link Account} if present, or an empty {@link Optional} if no account with the given email exists
     */
    public Optional<Account> findByEmail(String email);
}