package project.testmaster.backend.service;

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
}