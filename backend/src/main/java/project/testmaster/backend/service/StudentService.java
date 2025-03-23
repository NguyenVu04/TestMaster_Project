package project.testmaster.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.model.Student;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;

    public Student registerStudent(SignupRequestDTO request) {
        User user = userService.createUser(
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getPassword());

        Student student = new Student(user);

        return studentRepository.save(student);
    }
}