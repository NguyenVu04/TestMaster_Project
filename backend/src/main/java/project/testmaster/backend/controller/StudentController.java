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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.testmaster.backend.dto.*;
import project.testmaster.backend.model.*;
import project.testmaster.backend.service.ExamService;
import project.testmaster.backend.service.StudentService;
import project.testmaster.backend.utils.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/student")
@SecuritySchemes({
        @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
})
public class StudentController {
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Get exam", description = "Get exam questions and answers", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exam questions and answers"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "500", description = "Failed to get exam")
    })
    @PostMapping(path = "exam/session")
    public ResponseEntity<SessionResponseDTO> getExam(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Exam request", required = true) @Valid @RequestBody SessionRequestDTO request) {
        try {
            UUID studentId = userUtils.getCurrentUserId();

            ExamSession session = examService
                    .getExamSession(
                            UUID.fromString(
                                    request.getExamId()),
                            studentId,
                            request.getAttemptId());

            Exam exam = session.getExam();

            List<ExamQuestion> questions = exam.getExamQuestions();

            List<StudentAnswer> answers = session.getStudentAnswers();

            Map<UUID, StudentAnswer> answerMap = answers.stream()
                    .collect(Collectors.toMap(
                            answer -> answer.getQuestion().getId(),
                            answer -> answer
                    ));
            List<SessionQuestionDTO> questionDTOs = questions
                    .stream()
                    .map(question -> new SessionQuestionDTO(
                            question.getQuestion().getId().toString(),
                            question.getQuestion().getContent(),
                            question.getQuestion().getOptions().toArray(new String[0]),
                            question.getQuestion().getType(),
                            question.getQuestion().getMediaUrl().toArray(new String[0]),
                            !answerMap.containsKey(question.getQuestion().getId()) ? null
                                    : answerMap.get(question.getQuestion().getId()).getAnswer(),
                            question.getScore()))
                    .toList();

            SessionResponseDTO response = new SessionResponseDTO(
                    questionDTOs.toArray(new SessionQuestionDTO[0]),
                    session
                            .getStartTime()
                            .getTime(),
                    session
                            .getEndTime()
                            .getTime(),
                    exam
                            .getTimeLimit(),
                    session.isSubmitted());

            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException | NullPointerException e) {

            logger.error("Exam not found", e);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {

            logger.error("Failed to get exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    @Operation(summary = "Start exam", description = "Start exam session", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exam session started"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "500", description = "Failed to start exam")
    })
    @PostMapping(path = "exam/start")
    public ResponseEntity<StartExamResponseDTO> startExam(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Start exam request", required = true)
            @Valid @RequestBody StartExamRequestDTO request) {
        try {
            UUID studentId = userUtils.getCurrentUserId();

            ExamSessionId sessionId = examService.startExamSession(
                    UUID.fromString(
                            request.getExamId()),
                    studentId,
                    request.getPasscode());
            StartExamResponseDTO response = new StartExamResponseDTO(
                    sessionId.getAttemptId(),
                    sessionId.getExamId().toString(),
                    sessionId.getStudentId().toString());

            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {

            logger.error("Exam not found", e);
            return ResponseEntity.notFound().build();

        } catch (IllegalArgumentException e) {

            logger.error("Unauthorized access", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {

            logger.error("Failed to start exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    @Operation(summary = "Submit exam", description = "Submit exam session", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exam session submitted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "500", description = "Failed to submit exam")
    })
    @PostMapping(path = "exam/submit")
    public ResponseEntity<Void> submitExam(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Submit exam request", required = true)
            @Valid @RequestBody ExamSubmitRequestDTO request) {
        try {

            UUID studentId = userUtils.getCurrentUserId();

            Map<UUID, String> answers = new HashMap<>();

            for (StudentAnswerDTO answer : request.getAnswers()) {
                answers.put(UUID.fromString(answer.getQuestionId()), answer.getAnswer());
            }

            examService.submitExamSession(
                    request.getAttemptId(),
                    UUID.fromString(request.getExamId()),
                    UUID.fromString(studentId.toString()),
                    answers);

            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {

            logger.error("Unauthorized access", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (EntityNotFoundException | NullPointerException e) {

            logger.error("Exam not found", e);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {

            logger.error("Failed to submit exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    @Operation(summary = "Save exam", description = "Save exam session", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exam session saved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "500", description = "Failed to save exam")
    })
    @PostMapping(path = "exam/save")
    public ResponseEntity<Void> saveSession(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Submit exam request", required = true)
            @Valid @RequestBody ExamSubmitRequestDTO request) {
        try {

            UUID studentId = userUtils.getCurrentUserId();

            Map<UUID, String> answers = new HashMap<>();

            for (StudentAnswerDTO answer : request.getAnswers()) {
                answers.put(UUID.fromString(answer.getQuestionId()), answer.getAnswer());
            }

            examService.saveExamSession(
                    request.getAttemptId(),
                    UUID.fromString(request.getExamId()),
                    UUID.fromString(studentId.toString()),
                    answers);

            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {

            logger.error("Unauthorized access", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (EntityNotFoundException | NullPointerException e) {

            logger.error("Exam not found", e);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {

            logger.error("Failed to save exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    @Operation(summary = "Get results of all exams", description = "Get exam results", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exam results", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExamResultDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Failed to get exam results")
    })
    @GetMapping(path = "exam/results")
    public ResponseEntity<List<ExamResultDTO>> getExamResults() {
        try {

            UUID studentId = userUtils.getCurrentUserId();

            List<ExamResultDTO> results = studentService
                    .getStudentById(studentId)
                    .getExamResults()
                    .stream()
                    .filter(ExamSession::isSubmitted)
                    .map(ExamResultDTO::fromEntity)
                    .toList();

            return ResponseEntity.ok(results);

        } catch (Exception e) {

            logger.error("Failed to get exam result", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    @Operation(summary = "Get results of an exam", description = "Get exam result", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exam result", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExamResultDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Failed to get exam result")
    })
    @GetMapping(path = "exam/result")
    public ResponseEntity<List<ExamResultDTO>> getExamResult(@Parameter(name = "examId", in = ParameterIn.QUERY) @Valid @RequestParam(name = "examId") UUID examId) {
        try {

            UUID studentId = userUtils.getCurrentUserId();

            List<ExamResultDTO> results = examService
                    .getStudentExamSessions(examId, studentId)
                    .stream()
                    .filter(ExamSession::isSubmitted)
                    .map(ExamResultDTO::fromEntity)
                    .toList();

            return ResponseEntity.ok(results);

        } catch (Exception e) {

            logger.error("Failed to get exam result", e);
            return ResponseEntity.internalServerError().build();

        }
    }
}