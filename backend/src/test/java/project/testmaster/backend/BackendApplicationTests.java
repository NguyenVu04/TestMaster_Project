package project.testmaster.backend;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import project.testmaster.backend.model.Question;
import project.testmaster.backend.model.Question.QuestionType;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.repository.QuestionRepository;
import project.testmaster.backend.service.QuestionService;

@ExtendWith(MockitoExtension.class)
class BackendApplicationTests {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    private Question sampleQuestion;
    private UUID questionId;

    @BeforeEach
    void setUp() {
        questionId = UUID.randomUUID();
        Teacher teacher = new Teacher();
        sampleQuestion = new Question(teacher, QuestionType.MULTIPLE_CHOICE, "Sample Question", 
                                      new String[]{"media1.png"}, new String[]{"A", "B", "C", "D"}, "A");
        sampleQuestion.setId(questionId);
    }

    @Test
    void testCreateQuestion_Success() {
        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(sampleQuestion);
        Question created = questionService.createQuestion(sampleQuestion.getTeacher(), sampleQuestion.getType(),
                                                          sampleQuestion.getContent(), new String[]{"media1.png"},
                                                          new String[]{"A", "B", "C", "D"}, sampleQuestion.getAnswer());
        assertNotNull(created);
        assertEquals("Sample Question", created.getContent());
    }

    @Test
    void testCreateQuestion_Failure() {
        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> {
            questionService.createQuestion(sampleQuestion.getTeacher(), sampleQuestion.getType(),
                                            sampleQuestion.getContent(), new String[]{"media1.png"},
                                            new String[]{"A", "B", "C", "D"}, sampleQuestion.getAnswer());
        });
    }

    @Test
    void testGetQuestionById_Success() {
        Mockito.when(questionRepository.findById(questionId)).thenReturn(Optional.of(sampleQuestion));
        Question retrieved = questionService.getQuestionById(questionId);
        assertNotNull(retrieved);
        assertEquals(questionId, retrieved.getId());
    }


    @Test
    void testDeleteQuestion_Success() {
        questionService.deleteQuestion(questionId);
        Mockito.verify(questionRepository, Mockito.times(1)).deleteById(questionId);
    }

    @Test
    void testDeleteQuestion_Failure() {
        Mockito.doThrow(new RuntimeException("Deletion failed"))
               .when(questionRepository).deleteById(questionId);
        assertThrows(RuntimeException.class, () -> questionService.deleteQuestion(questionId));
    }

    @Test
    void testUpdateQuestion_Success() {
        sampleQuestion.setContent("Updated Question");
        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(sampleQuestion);
        Question updated = questionService.updateQuestion(sampleQuestion);
        assertEquals("Updated Question", updated.getContent());
    }

    @Test
    void testUpdateQuestion_Failure() {
        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenThrow(new RuntimeException("Update failed"));
        assertThrows(RuntimeException.class, () -> questionService.updateQuestion(sampleQuestion));
    }
}
