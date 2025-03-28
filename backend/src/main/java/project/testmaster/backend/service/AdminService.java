package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final UserService userService;

    public AdminService(UserService userService) {
        this.userService = userService;
    }

    public UUID login(String email, String password) {

        return userService.login(email, password);
    }
}
