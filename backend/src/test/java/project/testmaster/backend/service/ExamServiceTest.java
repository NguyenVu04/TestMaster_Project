package project.testmaster.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import project.testmaster.backend.model.*;
import project.testmaster.backend.repository.ExamRepository;
import project.testmaster.backend.repository.ExamSessionRepository;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private ExamSessionRepository examSessionRepository;

    @InjectMocks
    private ExamService examService;

    private UUID examId;
    private UUID studentId;
    private UUID teacherId;
    private Exam exam;
    private ExamSession examSession;
    private short attemptId;
    private Teacher teacher;
    private Student student;

    @BeforeEach
    void setUp() {
        examId = UUID.randomUUID();
        studentId = UUID.randomUUID();
        teacherId = UUID.randomUUID();
        attemptId = 1;

        // Tạo teacher và student
        teacher = new Teacher(teacherId);
        student = new Student(studentId);

        // Tạo đối tượng exam test
        exam = new Exam();
        exam.setId(examId);
        exam.setTitle("Test Exam");
        exam.setDescription("This is a test exam");
        exam.setPasscode("test123");
        exam.setAttemptLimit((short) 3);
        exam.setTimeLimit(3600); // 1 hour
        
        // Thời gian bắt đầu và kết thúc exam (đang diễn ra)
        exam.setStartTime(Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
        exam.setEndTime(Timestamp.from(Instant.now().plus(Duration.ofHours(1))));
        
        // Tạo đối tượng exam session test
        examSession = new ExamSession(
            attemptId,
            exam,
            student,
            null,
            Timestamp.from(Instant.now()),
            Timestamp.from(Instant.now().plus(Duration.ofHours(1)))
        );
        
        // ID của session
        ExamSessionId sessionId = new ExamSessionId();
        sessionId.setAttemptId(attemptId);
        sessionId.setExamId(examId);
        sessionId.setStudentId(studentId);
        examSession.setId(sessionId);
    }

    @Test
    void getExamById_ExistingId_ReturnsExam() {
        // Arrange
        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));

        // Act
        Exam result = examService.getExamById(examId);

        // Assert
        assertNotNull(result);
        assertEquals(examId, result.getId());
        assertEquals("Test Exam", result.getTitle());
        assertEquals("This is a test exam", result.getDescription());
        verify(examRepository).findById(examId);
    }

    @Test
    void getExamById_NonExistingId_ReturnsNull() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(examRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act
        Exam result = examService.getExamById(nonExistingId);

        // Assert
        assertNull(result);
        verify(examRepository).findById(nonExistingId);
    }

    @Test
    void saveExam_NewExam_ReturnsSavedExam() {
        // Arrange
        Exam newExam = new Exam(
            teacher, 
            "New Exam", 
            "New Description", 
            (short) 2, 
            "newpass", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now().plus(Duration.ofHours(2))), 
            7200
        );
        
        when(examRepository.save(newExam)).thenReturn(newExam);

        // Act
        Exam result = examService.saveExam(newExam);

        // Assert
        assertNotNull(result);
        assertEquals("New Exam", result.getTitle());
        assertEquals("New Description", result.getDescription());
        assertEquals("newpass", result.getPasscode());
        verify(examRepository).save(newExam);
    }

    @Test
    void deleteExam_ExistingId_CallsDeleteById() {
        // Act
        examService.deleteExam(examId);

        // Assert
        verify(examRepository).deleteById(examId);
    }

    @Test
    void updateExam_ExistingExam_ReturnsUpdatedExam() {
        // Arrange
        exam.setTitle("Updated Title");
        exam.setDescription("Updated Description");
        
        when(examRepository.existsById(examId)).thenReturn(true);
        when(examRepository.save(exam)).thenReturn(exam);

        // Act
        Exam result = examService.updateExam(exam);

        // Assert
        assertNotNull(result);
        assertEquals(examId, result.getId());
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        verify(examRepository).existsById(examId);
        verify(examRepository).save(exam);
    }

    @Test
    void updateExam_NonExistingExam_ThrowsEntityNotFoundException() {
        // Arrange
        when(examRepository.existsById(examId)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> examService.updateExam(exam));
        
        assertEquals("Exam not found", exception.getMessage());
        verify(examRepository).existsById(examId);
        verify(examRepository, never()).save(any(Exam.class));
    }

    @Test
    void getExamSession_ValidSession_ReturnsSession() {
        // Arrange
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(examSession);

        // Act
        ExamSession result = examService.getExamSession(examId, studentId, attemptId);

        // Assert
        assertNotNull(result);
        assertEquals(examId, result.getExam().getId());
        assertEquals(studentId, result.getStudent().getId());
        assertEquals(attemptId, result.getId().getAttemptId());
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
    }

    @Test
    void getExamSession_NonExistingSession_ReturnsNull() {
        // Arrange
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(null);

        // Act
        ExamSession result = examService.getExamSession(examId, studentId, attemptId);

        // Assert
        assertNull(result);
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
    }

    @Test
    void getStudentExamSessions_ExistingSessions_ReturnsList() {
        // Arrange
        List<ExamSession> sessions = Collections.singletonList(examSession);
        when(examSessionRepository.findByExamIdAndStudentId(examId, studentId)).thenReturn(sessions);

        // Act
        List<ExamSession> result = examService.getStudentExamSessions(examId, studentId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(examId, result.get(0).getExam().getId());
        assertEquals(studentId, result.get(0).getStudent().getId());
        verify(examSessionRepository).findByExamIdAndStudentId(examId, studentId);
    }

    @Test
    void getStudentExamSessions_NoSessions_ReturnsEmptyList() {
        // Arrange
        when(examSessionRepository.findByExamIdAndStudentId(examId, studentId))
                .thenReturn(Collections.emptyList());

        // Act
        List<ExamSession> result = examService.getStudentExamSessions(examId, studentId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(examSessionRepository).findByExamIdAndStudentId(examId, studentId);
    }

    @Test
    void getExamSessions_ExistingSessions_ReturnsList() {
        // Arrange
        List<ExamSession> sessions = Collections.singletonList(examSession);
        when(examSessionRepository.findByExamId(examId)).thenReturn(sessions);

        // Act
        List<ExamSession> result = examService.getExamSessions(examId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(examId, result.get(0).getExam().getId());
        verify(examSessionRepository).findByExamId(examId);
    }

    @Test
    void startExamSession_ValidExamAndPasscode_CreatesNewSession() {
        // Arrange
        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
        when(examSessionRepository.findByExamIdAndStudentId(examId, studentId))
                .thenReturn(new ArrayList<>());
        when(examSessionRepository.save(any(ExamSession.class))).thenAnswer(invocation -> {
            ExamSession savedSession = invocation.getArgument(0);
            return savedSession;
        });

        // Act
        ExamSessionId result = examService.startExamSession(examId, studentId, "test123");

        // Assert
        assertNotNull(result);
        assertEquals(examId, result.getExamId());
        assertEquals(studentId, result.getStudentId());
        assertEquals(1, result.getAttemptId());
        verify(examRepository).findById(examId);
        verify(examSessionRepository).findByExamIdAndStudentId(examId, studentId);
        verify(examSessionRepository).save(any(ExamSession.class));
    }

    @Test
    void startExamSession_ExamNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        when(examRepository.findById(examId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> examService.startExamSession(examId, studentId, "test123"));
        
        assertEquals("Exam not found", exception.getMessage());
        verify(examRepository).findById(examId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void startExamSession_InvalidPasscode_ThrowsIllegalArgumentException() {
        // Arrange
        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> examService.startExamSession(examId, studentId, "wrongpasscode"));
        
        assertTrue(exception.getMessage().contains("Invalid passcode"));
        verify(examRepository).findById(examId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void startExamSession_ExamNotStartedYet_ThrowsIllegalArgumentException() {
        // Arrange
        Exam notStartedExam = new Exam();
        notStartedExam.setId(examId);
        notStartedExam.setPasscode("test123");
        // Thời gian bắt đầu trong tương lai
        notStartedExam.setStartTime(Timestamp.from(Instant.now().plus(Duration.ofHours(1))));
        notStartedExam.setEndTime(Timestamp.from(Instant.now().plus(Duration.ofHours(2))));
        
        when(examRepository.findById(examId)).thenReturn(Optional.of(notStartedExam));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> examService.startExamSession(examId, studentId, "test123"));
        
        assertEquals("Exam has not started yet", exception.getMessage());
        verify(examRepository).findById(examId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void startExamSession_ExamEnded_ThrowsIllegalArgumentException() {
        // Arrange
        Exam endedExam = new Exam();
        endedExam.setId(examId);
        endedExam.setPasscode("test123");
        // Thời gian kết thúc trong quá khứ
        endedExam.setStartTime(Timestamp.from(Instant.now().minus(Duration.ofHours(2))));
        endedExam.setEndTime(Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
        
        when(examRepository.findById(examId)).thenReturn(Optional.of(endedExam));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> examService.startExamSession(examId, studentId, "test123"));
        
        assertEquals("Exam has ended", exception.getMessage());
        verify(examRepository).findById(examId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void startExamSession_AttemptLimitExceeded_ThrowsIllegalArgumentException() {
        // Arrange
        List<ExamSession> existingSessions = new ArrayList<>();
        for (short i = 1; i <= 3; i++) {
            ExamSession session = new ExamSession();
            // Set submitted to true
            session.setSubmitted(10.0f);
            existingSessions.add(session);
        }
        
        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
        when(examSessionRepository.findByExamIdAndStudentId(examId, studentId))
                .thenReturn(existingSessions);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> examService.startExamSession(examId, studentId, "test123"));
        
        assertEquals("Attempt limit exceeded", exception.getMessage());
        verify(examRepository).findById(examId);
        verify(examSessionRepository).findByExamIdAndStudentId(examId, studentId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void startExamSession_UnsubmittedSession_ReturnsExistingSessionId() {
        // Arrange
        ExamSession unsubmittedSession = new ExamSession();
        unsubmittedSession.setId(examSession.getId());
        unsubmittedSession.setSubmitted(null); // Chưa submit
        
        List<ExamSession> existingSessions = Collections.singletonList(unsubmittedSession);
        
        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
        when(examSessionRepository.findByExamIdAndStudentId(examId, studentId))
                .thenReturn(existingSessions);

        // Act
        ExamSessionId result = examService.startExamSession(examId, studentId, "test123");

        // Assert
        assertNotNull(result);
        assertEquals(examId, result.getExamId());
        assertEquals(studentId, result.getStudentId());
        verify(examRepository).findById(examId);
        verify(examSessionRepository).findByExamIdAndStudentId(examId, studentId);
        // Không gọi save vì trả về session cũ
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void deleteExamSession_ValidSession_CallsDelete() {
        // Act
        examService.deleteExamSession(examId, studentId, attemptId);

        // Assert
        verify(examSessionRepository).deleteByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
    }

    @Test
    void submitExamSession_ValidSession_SubmitsAndSaves() {
        // Arrange
        List<ExamQuestion> questions = new ArrayList<>();
        Question question1 = new Question();
        UUID questionId1 = UUID.randomUUID();
        question1.setId(questionId1);
        question1.setAnswer("correct answer");
        
        ExamQuestion examQuestion1 = new ExamQuestion();
        examQuestion1.setQuestion(question1);
        examQuestion1.setAutoScore(true);
        examQuestion1.setScore(10.0f);
        questions.add(examQuestion1);
        
        exam.setExamQuestions(questions);
        examSession.setExam(exam);
        examSession.setSubmitted(null); // Chưa submit
        
        Map<UUID, String> answers = new HashMap<>();
        answers.put(questionId1, "correct answer");
        
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(examSession);

        // Act
        examService.submitExamSession(attemptId, examId, studentId, answers);

        // Assert
        assertTrue(examSession.isSubmitted());
        assertEquals(10.0f, examSession.getTotalScore());
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        verify(examSessionRepository).save(examSession);
    }

    @Test
    void submitExamSession_IncorrectAnswer_CalculatesZeroScore() {
        // Arrange
        List<ExamQuestion> questions = new ArrayList<>();
        Question question1 = new Question();
        UUID questionId1 = UUID.randomUUID();
        question1.setId(questionId1);
        question1.setAnswer("correct answer");
        
        ExamQuestion examQuestion1 = new ExamQuestion();
        examQuestion1.setQuestion(question1);
        examQuestion1.setAutoScore(true);
        examQuestion1.setScore(10.0f);
        questions.add(examQuestion1);
        
        exam.setExamQuestions(questions);
        examSession.setExam(exam);
        examSession.setSubmitted(null); // Chưa submit
        
        Map<UUID, String> answers = new HashMap<>();
        answers.put(questionId1, "incorrect answer");
        
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(examSession);

        // Act
        examService.submitExamSession(attemptId, examId, studentId, answers);

        // Assert
        assertTrue(examSession.isSubmitted());
        assertEquals(0.0f, examSession.getTotalScore());
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        verify(examSessionRepository).save(examSession);
    }

    @Test
    void submitExamSession_SessionNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(null);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> examService.submitExamSession(attemptId, examId, studentId, new HashMap<>()));
        
        assertEquals("Exam session not found", exception.getMessage());
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void submitExamSession_AlreadySubmitted_ThrowsIllegalArgumentException() {
        // Arrange
        examSession.setSubmitted(5.0f); // Đã submit
        
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(examSession);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> examService.submitExamSession(attemptId, examId, studentId, new HashMap<>()));
        
        assertEquals("Exam session already submitted", exception.getMessage());
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void submitExamSession_SessionEnded_UsesExistingAnswers() {
        // Arrange
        // Tạo question và đáp án
        List<ExamQuestion> questions = new ArrayList<>();
        Question question1 = new Question();
        UUID questionId1 = UUID.randomUUID();
        question1.setId(questionId1);
        question1.setAnswer("correct answer");
        
        ExamQuestion examQuestion1 = new ExamQuestion();
        examQuestion1.setQuestion(question1);
        examQuestion1.setAutoScore(true);
        examQuestion1.setScore(10.0f);
        questions.add(examQuestion1);
        
        // Tạo student answer đã lưu trước đó
        List<StudentAnswer> studentAnswers = new ArrayList<>();
        StudentAnswer sa = new StudentAnswer();
        sa.setQuestion(question1);
        sa.setAnswer("correct answer");
        studentAnswers.add(sa);
        
        // Setup session đã kết thúc thời gian làm bài
        exam.setExamQuestions(questions);
        examSession.setExam(exam);
        examSession.setSubmitted(null); // Chưa submit
        examSession.setStudentAnswers(studentAnswers);
        examSession.setEndTime(Timestamp.from(Instant.now().minus(Duration.ofMinutes(5)))); // Đã kết thúc
        
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(examSession);

        // Act
        examService.submitExamSession(attemptId, examId, studentId, new HashMap<>()); // Empty answers

        // Assert
        assertTrue(examSession.isSubmitted());
        assertEquals(10.0f, examSession.getTotalScore()); // Vẫn được điểm dựa trên câu trả lời đã lưu
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        verify(examSessionRepository).save(examSession);
    }

    @Test
    void saveExamSession_ValidSession_SavesAnswers() {
        // Arrange
        examSession.setSubmitted(null); // Chưa submit
        Map<UUID, String> answers = new HashMap<>();
        UUID questionId = UUID.randomUUID();
        answers.put(questionId, "test answer");
        
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(examSession);

        // Act
        examService.saveExamSession(attemptId, examId, studentId, answers);

        // Assert
        assertNotNull(examSession.getStudentAnswers());
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        verify(examSessionRepository).save(examSession);
    }

    @Test
    void saveExamSession_SessionNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(null);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> examService.saveExamSession(attemptId, examId, studentId, new HashMap<>()));
        
        assertEquals("Exam session not found", exception.getMessage());
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void saveExamSession_AlreadySubmitted_ThrowsIllegalArgumentException() {
        // Arrange
        examSession.setSubmitted(5.0f); // Đã submit
        
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(examSession);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> examService.saveExamSession(attemptId, examId, studentId, new HashMap<>()));
        
        assertEquals("Exam session already submitted", exception.getMessage());
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }

    @Test
    void saveExamSession_SessionEnded_ThrowsIllegalArgumentException() {
        // Arrange
        examSession.setSubmitted(null); // Chưa submit
        // Thời gian kết thúc trong quá khứ
        examSession.setEndTime(Timestamp.from(Instant.now().minus(Duration.ofMinutes(5))));
        
        when(examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId))
                .thenReturn(examSession);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> examService.saveExamSession(attemptId, examId, studentId, new HashMap<>()));
        
        assertEquals("Exam session has ended", exception.getMessage());
        verify(examSessionRepository).findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        verify(examSessionRepository, never()).save(any(ExamSession.class));
    }
}
