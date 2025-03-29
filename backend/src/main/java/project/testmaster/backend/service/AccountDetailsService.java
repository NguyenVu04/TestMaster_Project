package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.testmaster.backend.repository.AccountRepository;

/**
 * Service implementation of {@link UserDetailsService} for handling operations
 * related to user account authentication details.
 *
 * This service interacts with the {@link AccountRepository} to retrieve user
 * details based on a provided user identifier. The user identifier represents
 * the UUID of a user account in the system.
 *
 * The service is responsible for loading a user's authentication details, such as
 * username and credentials, based on the unique identifier. It is commonly used
 * in Spring Security for handling authentication processes.
 *
 * Throws {@link UsernameNotFoundException} if no user with the given identifier
 * exists in the system.
 */
@Service
public class AccountDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Loads the user's authentication details based on the provided user identifier.
     * The method retrieves the user information from the repository by converting
     * the supplied identifier to a UUID format and fetching the associated user details.
     *
     * Throws {@link UsernameNotFoundException} if no user with the given identifier exists.
     *
     * @param id the unique identifier (as a String) of the user for which authentication details are to be fetched
     * @return the {@link UserDetails} containing the user's authentication details
     * @throws UsernameNotFoundException if the user identifier does not correspond to any existing user
     */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return accountRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
