package project.testmaster.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import project.testmaster.backend.dto.ExamSubmitRequestDTO;
import project.testmaster.backend.dto.SessionQuestionDTO;
import project.testmaster.backend.dto.SessionRequestDTO;
import project.testmaster.backend.dto.SessionResponseDTO;
import project.testmaster.backend.dto.StartExamRequestDTO;
import project.testmaster.backend.dto.StartExamResponseDTO;
import project.testmaster.backend.dto.StudentAnswerDTO;
import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.Exam;
import project.testmaster.backend.model.ExamQuestion;
import project.testmaster.backend.model.ExamSession;
import project.testmaster.backend.model.ExamSessionId;
import project.testmaster.backend.model.StudentAnswer;
import project.testmaster.backend.service.ExamService;

@RestController
@RequestMapping(path = "/api/student")
@SecuritySchemes({
        @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
})
public class StudentController {
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private ExamService examService;

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

            SecurityContext context = SecurityContextHolder.getContext();
            Account account = (Account) context.getAuthentication().getPrincipal();
            UUID studentId = account.getUserId();

            ExamSession session = examService
                    .getExamSession(
                            UUID.fromString(
                                    request.getExamId()),
                            studentId,
                            request.getAttemptId());

            Exam exam = session.getExam();

            List<ExamQuestion> questions = exam.getExamQuestions();

            List<StudentAnswer> answers = session.getStudentAnswers();

            Map<UUID, StudentAnswer> answerMap = answers
                    .stream()
                    .collect(
                            Map::of,
                            (map, answer) -> map.put(answer.getQuestion().getId(), answer),
                            Map::putAll);
            List<SessionQuestionDTO> questionDTOs = questions
                    .stream()
                    .map(question -> new SessionQuestionDTO(
                            question.getQuestion().getId().toString(),
                            question.getQuestion().getContent(),
                            question.getQuestion().getOptions().toArray(new String[0]),
                            question.getQuestion().getType(),
                            question.getQuestion().getMediaUrl().toArray(new String[0]),
                            answerMap.containsKey(question.getQuestion().getId()) ? null
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

            SecurityContext context = SecurityContextHolder.getContext();
            Account account = (Account) context.getAuthentication().getPrincipal();
            UUID studentId = account.getUserId();

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

            Map<UUID, String> answers = new HashMap<>();

            for (StudentAnswerDTO answer : request.getAnswers()) {
                answers.put(UUID.fromString(answer.getQuestionId()), answer.getAnswer());
            }

            examService.submitExamSession(
                    request.getAttemptId(),
                    UUID.fromString(request.getExamId()),
                    UUID.fromString(request.getStudentId()),
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

            Map<UUID, String> answers = new HashMap<>();

            for (StudentAnswerDTO answer : request.getAnswers()) {
                answers.put(UUID.fromString(answer.getQuestionId()), answer.getAnswer());
            }

            examService.saveExamSession(
                    request.getAttemptId(),
                    UUID.fromString(request.getExamId()),
                    UUID.fromString(request.getStudentId()),
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
}
