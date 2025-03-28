package project.testmaster.backend.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.testmaster.backend.model.Account;

import java.util.UUID;

/**
 * Utility class for user-related operations.
 */
@Service
public class UserUtils {
    /**
     * Retrieves the user ID of the currently authenticated user.
     *
     * @return the UUID representing the ID of the currently authenticated user
     */
    public UUID getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        Account account = (Account) context.getAuthentication().getPrincipal();
        return account.getUserId();
    }
}