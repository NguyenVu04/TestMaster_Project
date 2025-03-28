package project.testmaster.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.testmaster.backend.dto.*;
import project.testmaster.backend.model.*;
import project.testmaster.backend.repository.TeacherRepository;
import project.testmaster.backend.service.ExamService;
import project.testmaster.backend.service.QuestionService;
import project.testmaster.backend.utils.UserUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class is a REST controller for managing operations related to teachers and their associated exams.
 * It provides endpoints for creating, retrieving, deleting, and listing exams associated with a teacher.
 *
 * The controller is secured using a bearer authentication scheme.
 * All operations require a valid authenticated teacher's access token.
 *
 * Annotations:
 * - @RestController: Indicates that this class serves as a RESTful web service controller.
 * - @RequestMapping: Maps all endpoints in this controller under the base path `/api/teacher`.
 * - @SecuritySchemes: Defines the security requirements for API methods using a bearer authentication scheme.
 *
 * Endpoints provided by this controller:
 * - POST /exam: Creates a new exam.
 * - GET /exam/{examId}: Retrieves a specific exam by its identifier.
 * - DELETE /exam/{examId}: Deletes a specific exam by its identifier.
 * - GET /exams: Retrieves a list of all exams associated with the authenticated teacher.
 *
 * Exception handling:
 * - Logs relevant error messages for each operation.
 * - Returns appropriate HTTP status codes based on the nature of the error, including NOT_FOUND, FORBIDDEN, INTERNAL_SERVER_ERROR, and CONFLICT.
 *
 * Dependencies:
 * - UserUtils: Utility class to obtain the currently authenticated user's ID.
 * - ExamService: Service for performing business logic related to exams.
 * - QuestionService: Service for creating and managing questions.
 * - TeacherRepository: Repository for accessing teacher-related database information.
 *
 * Response and request details:
 * - All endpoints use standard representations for both input and output.
 * - APIs leverage DTOs (Data Transfer Objects) for managing request and response data efficiently.
 * - OpenAPI annotations provide metadata about the operations, inputs, outputs, and possible responses.
 */
@RestController
@RequestMapping(path = "/api/teacher")
@SecuritySchemes({
        @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
})
public class TeacherController {

    private final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * Creates a new exam based on the provided {@code NewExamRequestDTO}.
     *
     * This method processes the input data, creates an exam entity, its associated questions,
     * and persists the information into the database. It handles conflicts and errors that
     * may occur during the creation process.
     *
     * @param newExam The request data containing information to create a new exam, including
     *                title, description, question details, and other exam configurations.
     * @return A ResponseEntity indicating the outcome of the operation. Returns:
     *         - 200 (OK) if the exam was created successfully.
     *         - 400 (Bad Request) if the input data is invalid.
     *         - 403 (Forbidden) if the user does not have adequate permissions.
     *         - 409 (Conflict) if there is conflict with the provided data.
     *         - 500 (Internal Server Error) for other unexpected errors.
     */
    @Operation(summary = "Create exam", description = "Create a new exam", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Conflict data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/exam")
    public ResponseEntity<Void> createExam(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New exam request",
                    content = @Content(
                            schema = @Schema(
                                    implementation = NewExamRequestDTO.class,
                                    example = """
                                            {
                                              "title": "Java Basics Exam",
                                              "description": "Exam covering Java fundamentals",
                                              "attemptLimit": 3,
                                              "startTime": 1689446400000,
                                              "endTime": 1704067200000,
                                              "timeLimit": 3600,
                                              "passcode": "1234",
                                              "selectedQuestions": [
                                                {
                                                  "questionId": "11111111-1111-1111-1111-111111111111",
                                                  "score": 5,
                                                  "autoScore": true,
                                                  "number": 1
                                                }
                                              ],
                                              "newQuestions": [
                                                {
                                                  "type": "MULTIPLE_CHOICE",
                                                  "content": "What is polymorphism in Java?",
                                                  "mediaUrl": [],
                                                  "options": ["Overloading", "Overriding", "Both", "None"],
                                                  "answer": "Both",
                                                  "score": 10,
                                                  "autoScore": false,
                                                  "number": 2
                                                }
                                              ]
                                            }""")))
            @Valid @RequestBody NewExamRequestDTO newExam
    ) {
        try {

            UUID teacherId = userUtils.getCurrentUserId();

            Timestamp startTime = newExam.getStartTime() != null
                    ? Timestamp.from(Instant.ofEpochMilli(newExam.getStartTime()))
                    : null;
            Timestamp endTime = newExam.getEndTime() != null
                    ? Timestamp.from(Instant.ofEpochMilli(newExam.getEndTime()))
                    : null;

            Exam exam = new Exam(
                    new Teacher(teacherId),
                    newExam.getTitle(),
                    newExam.getDescription(),
                    newExam.getAttemptLimit(),
                    newExam.getPasscode(),
                    startTime,
                    endTime,
                    newExam.getTimeLimit());

            exam.setExamQuestions(new ArrayList<>());

            exam = examService.saveExam(exam);

            for (SelectedQuestionRequestDTO question : newExam.getSelectedQuestions()) {
                exam.addExamQuestion(
                        new ExamQuestion(
                                exam,
                                new Question(
                                        UUID.fromString(
                                                question.getQuestionId())),
                                question.getScore(),
                                question.isAutoScore(),
                                question.getNumber()));
            }

            for (NewQuestionRequestDTO question : newExam.getNewQuestions()) {
                Question newQuestion = questionService.createQuestion(
                        new Teacher(teacherId),
                        question.getType(),
                        question.getContent(),
                        question.getMediaUrl(),
                        question.getOptions(),
                        question.getAnswer());

                exam.addExamQuestion(new ExamQuestion(
                        exam,
                        newQuestion,
                        question.getScore(),
                        question.isAutoScore(),
                        question.getNumber()));
            }

            examService.saveExam(exam);

            return ResponseEntity.ok().build();

        } catch (DataIntegrityViolationException e) {

            logger.error("Conflict data while creating exam", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (Exception e) {

            logger.error("Error while creating exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    /**
     * Retrieves the exam details by the provided exam ID.
     * Validates if the current teacher is authorized to access the exam.
     *
     * @param examId the ID of the exam to retrieve, provided as a path variable
     * @return a {@link ResponseEntity} containing the {@link TeacherExamViewDTO} if the exam is found and accessible,
     *         or appropriate HTTP status codes: 403 if access is forbidden, 404 if the exam is not found,
     *         and 500 for unexpected server errors
     */
    @Operation(summary = "Get exam", description = "Get exam by id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam found"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/exam/{examId}")
    public ResponseEntity<TeacherExamViewDTO> getExam(
            @Parameter(name = "examId", in = ParameterIn.PATH, description = "Exam id")
            @Valid @PathVariable String examId
    ) {
        try {

            UUID teacherId = userUtils.getCurrentUserId();

            Exam exam = examService.getExamById(UUID.fromString(examId));

            if (!exam.getTeacher().getUserId().equals(teacherId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            TeacherExamViewDTO examView = TeacherExamViewDTO.fromEntity(exam);

            return ResponseEntity.ok(examView);

        } catch (NullPointerException e) {

            logger.error("Exam not found", e);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {

            logger.error("Error while getting exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    /**
     * Deletes an exam identified by the given exam ID.
     * Only the teacher who created the exam can delete it.
     * Returns an appropriate HTTP status based on the operation's outcome.
     *
     * @param examId The unique identifier of the exam to be deleted.
     * @return ResponseEntity with status:
     *         - 200 (OK) if the exam is deleted successfully.
     *         - 404 (Not Found) if the exam does not exist.
     *         - 403 (Forbidden) if the current user is not allowed to delete the exam.
     *         - 500 (Internal Server Error) if an unexpected error occurs.
     */
    @Operation(summary = "Delete exam", description = "Delete exam by id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/exam/{examId}")
    public ResponseEntity<Void> deleteExam(
            @Parameter(name = "examId", in = ParameterIn.PATH, description = "Exam id")
            @Valid @PathVariable String examId
    ) {
        try {

            UUID teacherId = userUtils.getCurrentUserId();

            Exam exam = examService.getExamById(UUID.fromString(examId));

            if (!exam.getTeacher().getUserId().equals(teacherId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            examService.deleteExam(exam.getId());

            return ResponseEntity.ok().build();

        } catch (NullPointerException e) {

            logger.error("Exam not found", e);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {

            logger.error("Error while deleting exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    /**
     * Retrieves a list of all exams associated with the currently authenticated teacher.
     *
     * This method queries the database to find the teacher based on the current user's ID,
     * and retrieves all exams linked to that teacher. If the teacher is not found, a 404
     * status is returned. In case of any other issue, a 500 status is returned.
     *
     * @return ResponseEntity containing a list of {@link ExamInfoDTO} if exams are successfully retrieved.
     *         Possible HTTP status codes:
     *         - 200: Successfully retrieved exams.
     *         - 403: Access is forbidden.
     *         - 404: Teacher not found.
     *         - 500: Internal server error.
     */
    @Operation(summary = "Get all exams", description = "Get all exams", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exams found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExamInfoDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Teacher not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(path = "/exams")
    public ResponseEntity<List<ExamInfoDTO>> getExams() {
        try {

            UUID teacherId = userUtils.getCurrentUserId();

            Teacher teacher = teacherRepository.findById(teacherId).orElse(null);

            if (teacher == null) {

                logger.error("Teacher not found when getting exams: {}", teacherId);
                return ResponseEntity.notFound().build();

            }

            List<ExamInfoDTO> infoDTOs = teacher
                    .getExams()
                    .stream()
                    .map(ExamInfoDTO::fromEntity)
                    .toList();

            return ResponseEntity.ok(infoDTOs);

        } catch (Exception e) {

            logger.error("Error while getting exams", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    /**
     * Retrieves a list of questions associated with the currently authenticated teacher.
     * This method validates the teacher's existence and maps the associated questions
     * into a list of {@link QuestionInfoDTO}.
     *
     * @return ResponseEntity containing a list of {@link QuestionInfoDTO} objects if
     *         successful, or an appropriate HTTP status code if an error occurs.
     *         - 200: Questions found.
     *         - 403: Access forbidden.
     *         - 404: Teacher not found.
     *         - 500: Internal server error.
     */
    @Operation(summary = "Get all questions", description = "Get all questions", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questions found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = QuestionInfoDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Teacher not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(path = "questions")
    public ResponseEntity<List<QuestionInfoDTO>> getQuestions() {
        try {
            UUID teacherId = userUtils.getCurrentUserId();

            Teacher teacher = teacherRepository.findById(teacherId).orElse(null);

            if (teacher == null) {

                logger.error("Teacher not found when getting questions: {}", teacherId);
                return ResponseEntity.notFound().build();

            }

            List<QuestionInfoDTO> infoDTOs = teacher
                    .getQuestions()
                    .stream()
                    .map(QuestionInfoDTO::fromEntity)
                    .toList();

            return ResponseEntity.ok(infoDTOs);

        } catch (Exception e) {

            logger.error("Error while getting questions", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    /**
     * Retrieves all results of students for a given exam.
     *
     * @param examId the unique identifier of the exam for which results are to be retrieved
     * @return a ResponseEntity containing a list of ExamResultDTO objects representing the exam results,
     * or an appropriate HTTP status if the operation fails (e.g., 403 if unauthorized, 404 if exam not found,
     * 500 if an internal server error occurs)
     */
    @Operation(summary = "Get all results of students in an exam", description = "Get all results of students in an exam", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam results found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExamResultDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("exam/results")
    public ResponseEntity<List<ExamResultDTO>> getExamResults(@Parameter(name = "examId", in = ParameterIn.QUERY) @Valid @RequestParam(name = "examId") UUID examId) {
        try {

            UUID teacherId = userUtils.getCurrentUserId();

            Exam exam = examService.getExamById(examId);

            if (exam == null) {

                logger.error("Exam not found when getting exam results: {}", examId);
                return ResponseEntity.notFound().build();

            }

            if (!exam.getTeacher().getUserId().equals(teacherId)) {

                logger.error("Teacher not authorized to get exam results: {}", teacherId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            }

            List<ExamResultDTO> results = examService
                    .getExamSessions(examId)
                    .stream()
                    .filter(ExamSession::isSubmitted)
                    .map(ExamResultDTO::fromEntity)
                    .toList();

            return ResponseEntity.ok(results);

        } catch (Exception e) {

            logger.error("Error while getting exam results", e);
            return ResponseEntity.internalServerError().build();

        }
    }
}