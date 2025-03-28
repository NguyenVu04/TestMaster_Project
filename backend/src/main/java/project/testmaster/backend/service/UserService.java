package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final AccountService accountService;

    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

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

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

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
