package project.testmaster.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.testmaster.backend.dto.StudentSignupRequest;
import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.Student;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.AccountRepository;
import project.testmaster.backend.repository.StudentRepository;
import project.testmaster.backend.repository.UserRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Student registerStudent(StudentSignupRequest request) {

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user = userRepository.save(user);

        // Create new account
        Account account = new Account(
            user, 
            request.getEmail(), 
            passwordEncoder
                .encode(request.getPassword()));
        accountRepository.save(account);

        // Create new student
        Student student = new Student(user);

        return studentRepository.save(student);
    }
}