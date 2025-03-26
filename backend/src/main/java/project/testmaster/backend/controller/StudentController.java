package project.testmaster.backend.controller;

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

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import project.testmaster.backend.dto.SessionQuestionDTO;
import project.testmaster.backend.dto.SessionRequestDTO;
import project.testmaster.backend.dto.SessionResponseDTO;
import project.testmaster.backend.dto.StartExamRequestDTO;
import project.testmaster.backend.dto.StartExamResponseDTO;
import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.Exam;
import project.testmaster.backend.model.ExamQuestion;
import project.testmaster.backend.model.ExamSession;
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

    @PostMapping(path = "exam")
    public ResponseEntity<SessionResponseDTO> getExam(@Valid @RequestBody SessionRequestDTO request) {
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
                            question.getQuestion().getAnswer(),
                            question.getQuestion().getType(),
                            question.getQuestion().getMediaUrl().toArray(new String[0]),
                            answerMap.get(question.getQuestion().getId()).getAnswer(),
                            question.getScore()
                    ))
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
                            .getTimeLimit());

            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {

            logger.error("Exam not found", e);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {

            logger.error("Failed to get exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }

    @PostMapping(path = "exam/start")
    public ResponseEntity<StartExamResponseDTO> startExam(@Valid @RequestBody StartExamRequestDTO request) {
        try {

            SecurityContext context = SecurityContextHolder.getContext();
            Account account = (Account) context.getAuthentication().getPrincipal();
            UUID studentId = account.getUserId();

            StartExamResponseDTO response = new StartExamResponseDTO(
                    examService.startExamSession(
                            UUID.fromString(
                                    request.getExamId()),
                            studentId,
                            request.getPasscode()));

            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {

            logger.error("Exam not found", e);
            return ResponseEntity.notFound().build();

        } catch (IllegalArgumentException e) {

            logger.error("Passcode is not correct", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {

            logger.error("Failed to start exam", e);
            return ResponseEntity.internalServerError().build();

        }
    }
}
