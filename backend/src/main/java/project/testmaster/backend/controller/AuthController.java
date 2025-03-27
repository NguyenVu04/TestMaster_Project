package project.testmaster.backend.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import project.testmaster.backend.dto.SigninRequestDTO;
import project.testmaster.backend.dto.SigninResponseDTO;
import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.service.StudentService;
import project.testmaster.backend.service.TeacherService;
import project.testmaster.backend.utils.JwtUtils;

@RestController()
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "Sign up a student", description = "Sign up a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student signed up successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request while signing up student"),
            @ApiResponse(responseCode = "409", description = "Conflict while signing up student"),
            @ApiResponse(responseCode = "500", description = "Internal server error while signing up student")
    })
    @PostMapping("/signup/student")
    public ResponseEntity<Void> signupStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The student to sign up", content = @Content(schema = @Schema(implementation = SignupRequestDTO.class), examples = @ExampleObject(value = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"1234567890\", \"email\": \"john.doe@email.com\", \"password\": \"password123\" }"))) @Valid @RequestBody SignupRequestDTO request) {
        try {

            studentService.registerStudent(request);
            return ResponseEntity.ok().build();

        } catch (DataIntegrityViolationException e) {

            logger.error("Conflict while registering student", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (Exception e) {

            logger.error("Error while registering student", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    @Operation(summary = "Sign in a student", description = "Sign in a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student signed in successfully", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request while signing in student"),
            @ApiResponse(responseCode = "401", description = "Unauthorized while signing in student"),
            @ApiResponse(responseCode = "500", description = "Internal server error while signing in student")
    })
    @PostMapping("/signin/student")
    public ResponseEntity<SigninResponseDTO> signinStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The student to sign in", content = @Content(schema = @Schema(implementation = SigninRequestDTO.class), examples = @ExampleObject(value = "{ \"email\": \"john.doe@example.com\", \"password\": \"admin\" }"))) @Valid @RequestBody SigninRequestDTO request) {
        try {

            UUID result = studentService.login(request.getEmail(), request.getPassword());

            if (result != null) {
                return ResponseEntity
                        .ok()
                        .body(new SigninResponseDTO(
                                jwtUtils.generateToken(
                                        result.toString(),
                                        "STUDENT")));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {

            logger.error("Error while signing in student", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    @Operation(summary = "Sign up a teacher", description = "Sign up a teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher signed up successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request while signing up teacher"),
            @ApiResponse(responseCode = "409", description = "Conflict while signing up teacher"),
            @ApiResponse(responseCode = "500", description = "Internal server error while signing up teacher")
    })
    @PostMapping("/signup/teacher")
    public ResponseEntity<Void> signupTeacher(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The teacher to sign up", content = @Content(schema = @Schema(implementation = SignupRequestDTO.class), examples = @ExampleObject(value = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"1234567890\", \"email\": \"john.doe@email.com\", \"password\": \"password123\" }"))) @Valid @RequestBody SignupRequestDTO request) {
        try {

            teacherService.registerTeacher(request);
            return ResponseEntity.ok().build();

        } catch (DataIntegrityViolationException e) {

            logger.error("Conflict while registering teacher", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (Exception e) {

            logger.error("Error while registering teacher", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    @Operation(summary = "Sign in a teacher", description = "Sign in a teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher signed in successfully", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request while signing in teacher"),
            @ApiResponse(responseCode = "401", description = "Unauthorized while signing in teacher"),
            @ApiResponse(responseCode = "500", description = "Internal server error while signing in teacher")
    })
    @PostMapping("/signin/teacher")
    public ResponseEntity<SigninResponseDTO> signinTeacher(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The teacher to sign in", content = @Content(schema = @Schema(implementation = SigninRequestDTO.class), examples = @ExampleObject(value = "{ \"email\": \"teach1@email.com\", \"password\": \"admin\" }"))) @Valid @RequestBody SigninRequestDTO request) {
        try {

            UUID result = teacherService.login(request.getEmail(), request.getPassword());

            if (result != null) {
                return ResponseEntity.ok().body(
                        new SigninResponseDTO(
                                jwtUtils.generateToken(
                                        result.toString(),
                                        "TEACHER")));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {

            logger.error("Error while signing in teacher", e);
            return ResponseEntity.internalServerError().build();

        }
    }
}
