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
import project.testmaster.backend.model.Exam;
import project.testmaster.backend.model.ExamQuestion;
import project.testmaster.backend.model.Question;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.repository.TeacherRepository;
import project.testmaster.backend.service.ExamService;
import project.testmaster.backend.service.QuestionService;
import project.testmaster.backend.utils.UserUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
}
