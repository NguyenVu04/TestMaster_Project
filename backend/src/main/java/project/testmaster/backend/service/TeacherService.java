package project.testmaster.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.AccountRepository;
import project.testmaster.backend.repository.TeacherRepository;
import project.testmaster.backend.repository.UserRepository;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Teacher registerTeacher(SignupRequestDTO request) throws Exception {
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber());
        user = userRepository.save(user);

        Account account = new Account(
                user,
                request.getEmail(),
                passwordEncoder
                        .encode(request.getPassword()));
        accountRepository.save(account);

        Teacher teacher = new Teacher(user);

        return teacherRepository.save(teacher);
    }
}