package project.testmaster.backend.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.validation.Valid;
import project.testmaster.backend.dto.NewExamRequestDTO;
import project.testmaster.backend.dto.NewQuestionRequestDTO;
import project.testmaster.backend.dto.SelectedQuestionRequestDTO;
import project.testmaster.backend.dto.TeacherExamViewDTO;
import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.Exam;
import project.testmaster.backend.model.ExamQuestion;
import project.testmaster.backend.model.Question;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.service.ExamService;
import project.testmaster.backend.service.QuestionService;

@RestController
@RequestMapping(path = "/api/teacher")
@SecuritySchemes({
        @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
})
public class TeacherController {

    private final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

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
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New exam request") 
            @Valid @RequestBody NewExamRequestDTO newExam
    ) {
        try {

            SecurityContext context = SecurityContextHolder.getContext();
            Account account = (Account) context.getAuthentication().getPrincipal();

            UUID teacherId = account.getUserId();

            Timestamp startTime = newExam.getStartTime() != null
                    ? Timestamp.from(Instant.ofEpochSecond(newExam.getStartTime()))
                    : null;
            Timestamp endTime = newExam.getEndTime() != null
                    ? Timestamp.from(Instant.ofEpochSecond(newExam.getEndTime()))
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
            
            exam = examService.saveExam(exam);

            return ResponseEntity.ok().build();

        } catch (DataIntegrityViolationException e) {

            logger.error("Conflict data while creating exam", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (Exception e) {

            logger.error("Error while creating exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }

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

            SecurityContext context = SecurityContextHolder.getContext();
            Account account = (Account) context.getAuthentication().getPrincipal();

            UUID teacherId = account.getUserId();

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

            SecurityContext context = SecurityContextHolder.getContext();
            Account account = (Account) context.getAuthentication().getPrincipal();

            UUID teacherId = account.getUserId();

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
}
