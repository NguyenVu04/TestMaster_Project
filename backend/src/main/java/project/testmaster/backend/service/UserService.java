package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(
        String firstName,
        String lastName,
        String phoneNumer,
        String email,
        String password
    ) {
        User user = new User(firstName, lastName, phoneNumer);
        userRepository.save(user);
        accountService.createAccount(user, email, password);
        return user;
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
