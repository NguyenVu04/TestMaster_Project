package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private UserService userService;

    public UUID login(String email, String password) {
        UUID id = userService.login(email, password);

        return id;
    }
}
