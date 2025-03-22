package project.testmaster.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.model.Student;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.service.StudentService;
import project.testmaster.backend.service.TeacherService;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/student/signup")
    public ResponseEntity<Student> signupStudent(@RequestBody SignupRequestDTO request) {
        return ResponseEntity.ok(studentService.registerStudent(request));
    }

    @PostMapping("/teacher/signup")
    public ResponseEntity<Teacher> signupTeacher(@RequestBody SignupRequestDTO request) {
        return ResponseEntity.ok(teacherService.registerTeacher(request));
    }
}
