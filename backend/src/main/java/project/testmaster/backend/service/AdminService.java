package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.testmaster.backend.repository.AdminRepository;

@Service
public class AdminService {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminRepository adminRepository;

    public boolean login(String email, String password) {
        UUID id = userService.login(email, password);

        return id != null && adminRepository.existsById(id);
    }
}
