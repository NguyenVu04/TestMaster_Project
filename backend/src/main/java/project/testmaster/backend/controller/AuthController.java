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

/**
 * Controller handling authentication-related operations for students and teachers.
 * Provides endpoints for signing up and signing in.
 */
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

    /**
     * Signs up a student by registering their details.
     * Handles various actions such as saving student information to the database
     * and managing errors such as conflicts or invalid data during the operation.
     *
     * @param request the student details required for signing up, encapsulated in a {@link SignupRequestDTO}.
     *                Includes fields like firstName, lastName, phoneNumber, email, and password.
     * @return a {@link ResponseEntity<Void>} representing the outcome of the signup process:
     *         - HTTP 200 if the signup is successful.
     *         - HTTP 400 if there is a bad request due to validation issues.
     *         - HTTP 409 if a conflict occurs, such as duplicate student details.
     *         - HTTP 500 if an internal server error occurs.
     */
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

    /**
     * Handles the student sign-in process.
     *
     * @param request the request body containing the student's email and password for sign-in.
     *                Must be a valid {@link SigninRequestDTO} object.
     * @return a {@link ResponseEntity} containing:
     *         - a {@link SigninResponseDTO} if the sign-in was successful (HTTP 200).
     *         - an HTTP 401 status if the provided credentials are unauthorized.
     *         - an HTTP 400 status if there is a bad request.
     *         - an HTTP 500 status in case of an internal server error.
     */
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

    /**
     * Handles the signing-up process for a teacher by receiving a signup request and processing it.
     * Validates the provided data and persists the teacher's information if valid.
     *
     * @param request The data required for signing up a teacher encapsulated in a {@code SignupRequestDTO} object.
     *                Contains fields such as first name, last name, phone number, email, and password.
     * @return A ResponseEntity object indicating the result of the signup operation:
     *         - 200 OK if the signup is successful.
     *         - 400 Bad Request if the provided input data is invalid.
     *         - 409 Conflict if there's an error due to a data integrity violation.
     *         - 500 Internal Server Error if an unexpected error occurs during the process.
     */
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

    /**
     * Signs in a teacher using the provided credentials.
     *
     * @param request The request containing the teacher's email and password.
     *                Must be a valid instance of {@link SigninRequestDTO}.
     * @return A {@link ResponseEntity} containing a {@link SigninResponseDTO} with a JWT token
     *         if the sign-in is successful, or an appropriate HTTP status in case of failure:
     *         - 200: Teacher signed in successfully
     *         - 400: Bad request
     *         - 401: Unauthorized
     *         - 500: Internal server error
     */
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
