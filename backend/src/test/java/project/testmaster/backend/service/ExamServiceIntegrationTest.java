package project.testmaster.backend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import project.testmaster.backend.model.Exam;
import project.testmaster.backend.model.Student;
import project.testmaster.backend.repository.AccountRepository;
import project.testmaster.backend.repository.ExamRepository;
import project.testmaster.backend.repository.QuestionRepository;
import project.testmaster.backend.repository.StudentRepository;

@SpringBootTest
@Transactional
class ExamServiceIntegrationTest {

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Exam testExam;
    private Student testStudent;
    private List<Question> testQuestions;

    @Test
    void getExamById_ShouldReturnCompleteExam() {
        // Act
        UUID testExamId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Exam result = examService.getExamById(testExamId);

        // Assert
        assertNotNull(result);

    }

}
