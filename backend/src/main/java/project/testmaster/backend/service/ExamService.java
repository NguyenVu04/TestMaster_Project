package project.testmaster.backend.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import project.testmaster.backend.model.Exam;
import project.testmaster.backend.model.ExamQuestion;
import project.testmaster.backend.model.ExamSession;
import project.testmaster.backend.model.ExamSessionId;
import project.testmaster.backend.model.Question;
import project.testmaster.backend.model.Student;
import project.testmaster.backend.model.StudentAnswer;
import project.testmaster.backend.repository.ExamRepository;
import project.testmaster.backend.repository.ExamSessionRepository;

@Service
public class ExamService {
    private final ExamRepository examRepository;

    private final ExamSessionRepository examSessionRepository;

    public ExamService(ExamRepository examRepository, ExamSessionRepository examSessionRepository) {
        this.examRepository = examRepository;
        this.examSessionRepository = examSessionRepository;
    }

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

    public List<ExamSession> getStudentExamSessions(UUID examId, UUID studentId) {
        return examSessionRepository.findByExamIdAndStudentId(examId, studentId);
    }

    public List<ExamSession> getExamSessions(UUID examId) {
        return examSessionRepository.findByExamId(examId);
    }

    public ExamSessionId startExamSession(UUID examId, UUID studentId, String passcode) {
        Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) {
            throw new EntityNotFoundException("Exam not found");
        }

        if (exam.getStartTime().after(Timestamp.from(Instant.now()))) {
            throw new IllegalArgumentException("Exam has not started yet");
        }

        if (exam.getEndTime().before(Timestamp.from(Instant.now()))) {
            throw new IllegalArgumentException("Exam has ended");
        }

        if (!exam.getPasscode().equals(passcode)) {
            throw new IllegalArgumentException("Invalid passcode: " + passcode);
        }

        List<ExamSession> sessions = examSessionRepository.findByExamIdAndStudentId(examId, studentId);
        short numberOfAttempts = (short) sessions.size();

        if (numberOfAttempts > 0 && !sessions.get(numberOfAttempts - 1).isSubmitted()) {
            return sessions.get(numberOfAttempts - 1).getId();
        }

        if (numberOfAttempts >= exam.getAttemptLimit()) {
            throw new IllegalArgumentException("Attempt limit exceeded");
        }

        Instant now = Instant.now();
        Instant endTime = now.plus(Duration.ofSeconds(exam.getTimeLimit()));

        ExamSession examSession = new ExamSession(
                (short) (numberOfAttempts + 1),
                exam,
                new Student(studentId),
                null,
                Timestamp.from(now),
                Timestamp.from(endTime));

        return examSessionRepository.save(examSession).getId();
    }

    public void deleteExamSession(UUID examId, UUID studentId, short attemptId) {
        examSessionRepository.deleteByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
    }

    public void submitExamSession(
            short attemptId,
            UUID examId,
            UUID studentId,
            Map<UUID, String> answers) {
        ExamSession session = examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        if (session == null) {
            throw new EntityNotFoundException("Exam session not found");
        }
        if (session.isSubmitted()) {
            throw new IllegalArgumentException("Exam session already submitted");
        }

        float totalScore = 0.0f;
        if (session.getEndTime().before(Timestamp.from(Instant.now()))) {
            List<StudentAnswer> studentAnswers = session.getStudentAnswers();

            answers = studentAnswers.stream()
                    .collect(
                            Collectors.toMap(
                                    studentAnswer -> studentAnswer.getQuestion().getId(),
                                    StudentAnswer::getAnswer));
        }

        List<ExamQuestion> questions = session.getExam().getExamQuestions();

        for (ExamQuestion question : questions) {
            UUID questionId = question.getQuestion().getId();
            String answer = answers.get(questionId);

            if (answer == null || !question.isAutoScore()) {
                continue;
            }

            if (question
                    .getQuestion()
                    .getAnswer()
                    .equals(answer)) {
                totalScore += question.getScore();
            }

        }

        if (!session.getEndTime().before(Timestamp.from(Instant.now()))) {
            updateStudentAnswers(answers, session);
        }

        session.setSubmitted(totalScore);

        examSessionRepository.save(session);
    }

    private void updateStudentAnswers(Map<UUID, String> answers, ExamSession session) {
        List<StudentAnswer> studentAnswers = new ArrayList<>();
        for (Map.Entry<UUID, String> entry : answers.entrySet()) {
            studentAnswers.add(new StudentAnswer(
                    session,
                    new Question(entry.getKey()),
                    entry.getValue()));
        }
        session.setStudentAnswers(studentAnswers);
    }

    public void saveExamSession(
            short attemptId,
            UUID examId,
            UUID studentId,
            Map<UUID, String> answers) {
                
        ExamSession session = examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
        if (session == null) {
            throw new EntityNotFoundException("Exam session not found");
        }
        if (session.isSubmitted()) {
            throw new IllegalArgumentException("Exam session already submitted");
        }
        if (session.getEndTime().before(Timestamp.from(Instant.now()))) {
            throw new IllegalArgumentException("Exam session has ended");            
        }

        updateStudentAnswers(answers, session);

        examSessionRepository.save(session);
    }
}
