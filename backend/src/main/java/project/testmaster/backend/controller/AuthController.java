package project.testmaster.backend.controller;

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
import project.testmaster.backend.dto.SignupRequestDTO;
import project.testmaster.backend.service.StudentService;
import project.testmaster.backend.service.TeacherService;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Operation(summary = "Sign up a student", description = "Sign up a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student signed up successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request while signing up student"),
            @ApiResponse(responseCode = "409", description = "Conflict while signing up student"),
            @ApiResponse(responseCode = "500", description = "Internal server error while signing up student")
    })
    @PostMapping("/signup/student")
    public ResponseEntity<Void> signupStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "The student to sign up", 
                content = @Content(
                    schema = @Schema(implementation = SignupRequestDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"1234567890\", \"email\": \"john.doe@email.com\", \"password\": \"password123\" }"
                    )
                )
            ) @Valid @RequestBody SignupRequestDTO request) {
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

    @Operation(summary = "Sign up a teacher", description = "Sign up a teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher signed up successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request while signing up teacher"),
            @ApiResponse(responseCode = "409", description = "Conflict while signing up teacher"),
            @ApiResponse(responseCode = "500", description = "Internal server error while signing up teacher")
    })
    @PostMapping("/signup/teacher")
    public ResponseEntity<Void> signupTeacher(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "The teacher to sign up", 
                content = @Content(
                    schema = @Schema(implementation = SignupRequestDTO.class),
                    examples = @ExampleObject(
                        value = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"1234567890\", \"email\": \"john.doe@email.com\", \"password\": \"password123\" }"
                    )
                )
            ) @Valid @RequestBody SignupRequestDTO request) {
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
}
