package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.TeacherRepository;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private UserService userService;

    public Teacher registerTeacher(SignupRequestDTO request) {
        User user = userService.createUser(
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getPassword());

        Teacher teacher = new Teacher(user);

        return teacherRepository.save(teacher);
    }

    public Teacher getTeacherById(UUID id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public boolean login(String email, String password) {
        UUID id = userService.login(email, password);

        return id != null && teacherRepository.existsById(id);
    }
}