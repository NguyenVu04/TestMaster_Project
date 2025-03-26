package project.testmaster.backend.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import project.testmaster.backend.model.Exam;
import project.testmaster.backend.model.ExamSession;
import project.testmaster.backend.model.ExamSessionId;
import project.testmaster.backend.model.Student;
import project.testmaster.backend.repository.ExamRepository;
import project.testmaster.backend.repository.ExamSessionRepository;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamSessionRepository examSessionRepository;

    public Exam getExamById(UUID id) {
        return examRepository.findById(id).orElse(null);
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public void deleteExam(UUID id) {
        examRepository.deleteById(id);
    }

    public Exam updateExam(Exam exam) {
        if (examRepository.existsById(exam.getId())) {
            return examRepository.save(exam);
        } else {
            throw new EntityNotFoundException("Exam not found");
        }
    }

    public ExamSession getExamSession(UUID examId, UUID studentId, short attemptId) {
        return examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
    }

    public ExamSessionId startExamSession(UUID examId, UUID studentId, String passcode) {
        Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) {
            throw new EntityNotFoundException("Exam not found");
        }

        if (!exam.getPasscode().equals(passcode)) {
            throw new IllegalArgumentException("Invalid passcode: " + passcode);
        }

        for (ExamSession session : exam.getExamSession()) {
            if (session
                    .getEndTime()
                    .after(Timestamp
                            .from(Instant.now()))) {

                return session.getId();

            }
        }

        Instant now = Instant.now();
        Instant endTime = now.plus(Duration.ofSeconds(exam.getTimeLimit()));

        ExamSession examSession = new ExamSession(
                exam,
                new Student(studentId),
                null,
                Timestamp.from(now),
                Timestamp.from(endTime));

        ExamSessionId sessionId = examSessionRepository.save(examSession).getId();

        return sessionId;
    }

    public void endExamSession(UUID examId, UUID studentId, short attemptId) {
        ExamSession session = examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        if (session == null) {
            throw new EntityNotFoundException("Exam session not found");
        }

        session.setSubmitted();
        examSessionRepository.save(session);
    }

    public void deleteExamSession(UUID examId, UUID studentId, short attemptId) {
        examSessionRepository.deleteByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
    }
}
