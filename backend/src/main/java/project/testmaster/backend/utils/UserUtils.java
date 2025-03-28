package project.testmaster.backend.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.testmaster.backend.model.Account;

import java.util.UUID;

@Service
public class UserUtils {
    public UUID getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        Account account = (Account) context.getAuthentication().getPrincipal();
        return account.getUserId();
    }
}