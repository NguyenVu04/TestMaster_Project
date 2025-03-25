package project.testmaster.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@RestController
@RequestMapping(path = "/api/teacher")
@SecuritySchemes({
    @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
})
public class TeacherController {
    @PostMapping("/exam")
    public ResponseEntity<Void> createExam() {
        return ResponseEntity.ok().build();
    }
}
