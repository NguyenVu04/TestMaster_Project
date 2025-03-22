package project.testmaster.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.testmaster.backend.dto.StudentSignupRequest;
import project.testmaster.backend.model.Student;
import project.testmaster.backend.service.StudentService;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    private final StudentService studentService;

    @Autowired
    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/student/signup")
    public ResponseEntity<Student> signup(@RequestBody StudentSignupRequest request) {
        return ResponseEntity.ok(studentService.registerStudent(request));
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello World");
    }
}
