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

/**
 * Service class that provides functionalities to manage exams and exam sessions.
 * This service is responsible for creating, retrieving, updating, and deleting exams,
 * as well as managing exam sessions for students.
 */
@Service
public class ExamService {
    private final ExamRepository examRepository;

    private final ExamSessionRepository examSessionRepository;

    /**
     * Constructs an instance of ExamService with the specified repositories.
     *
     * @param examRepository the repository used for accessing and managing exam data
     * @param examSessionRepository the repository used for accessing and managing exam session data
     */
    public ExamService(ExamRepository examRepository, ExamSessionRepository examSessionRepository) {
        this.examRepository = examRepository;
        this.examSessionRepository = examSessionRepository;
    }

    /**
     * Retrieves an exam by its unique identifier.
     *
     * @param id the unique identifier of the exam to retrieve
     * @return the Exam object if found, or null if no exam with the given ID exists
     */
    public Exam getExamById(UUID id) {
        return examRepository.findById(id).orElse(null);
    }

    /**
     * Saves the specified exam to the repository.
     *
     * @param exam the Exam object to be saved
     * @return the saved Exam object
     */
    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    /**
     * Deletes an exam identified by the given unique identifier.
     *
     * @param id the unique identifier of the exam to be deleted
     */
    public void deleteExam(UUID id) {
        examRepository.deleteById(id);
    }

    /**
     * Updates an existing exam in the repository. If the exam with the given ID exists,
     * it saves and returns the updated exam. If the exam does not exist, an exception is thrown.
     *
     * @param exam the exam object containing updated details
     * @return the updated exam object
     * @throws EntityNotFoundException if the exam with the given ID does not exist in the repository
     */
    public Exam updateExam(Exam exam) {
        if (examRepository.existsById(exam.getId())) {
            return examRepository.save(exam);
        } else {
            throw new EntityNotFoundException("Exam not found");
        }
    }

    /**
     * Retrieves an exam session based on the provided exam ID, student ID, and attempt ID.
     *
     * @param examId the unique identifier of the exam
     * @param studentId the unique identifier of the student
     * @param attemptId the identifier of the specific attempt for the exam
     * @return the exam session matching the given criteria, or null if no session is found
     */
    public ExamSession getExamSession(UUID examId, UUID studentId, short attemptId) {
        return examSessionRepository.findByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
    }

    /**
     * Retrieves a list of exam sessions for a specific student and exam.
     *
     * @param examId the unique identifier of the exam
     * @param studentId the unique identifier of the student
     * @return a list of exam sessions associated with the specified exam and student
     */
    public List<ExamSession> getStudentExamSessions(UUID examId, UUID studentId) {
        return examSessionRepository.findByExamIdAndStudentId(examId, studentId);
    }

    /**
     * Retrieves a list of exam sessions associated with the specified exam ID.
     *
     * @param examId the unique identifier of the exam whose sessions are to be retrieved
     * @return a list of ExamSession objects associated with the given exam ID
     */
    public List<ExamSession> getExamSessions(UUID examId) {
        return examSessionRepository.findByExamId(examId);
    }

    /**
     * Initiates a new exam session for a specific student for a given exam, ensuring all
     * preconditions, such as timing and passcode validation, are met. The method also enforces
     * attempt limits and resumes the previous session if it exists and is incomplete.
     *
     * @param examId the unique identifier of the exam to be started
     * @param studentId the unique identifier of the student initiating the session
     * @param passcode the passcode provided to validate access to the exam
     * @return the unique identifier of the created or resumed exam session
     * @throws EntityNotFoundException if the specified exam does not exist
     * @throws IllegalArgumentException if the exam has not started, has ended, the passcode is invalid,
     *                                  or the student has exceeded the maximum number of allowed attempts
     */
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

    /**
     * Deletes an exam session based on the provided exam ID, student ID, and attempt ID.
     *
     * @param examId The unique identifier for the exam.
     * @param studentId The unique identifier for the student.
     * @param attemptId The identifier for the specific attempt of the exam session.
     */
    public void deleteExamSession(UUID examId, UUID studentId, short attemptId) {
        examSessionRepository.deleteByExamIdAndStudentIdAndAttemptId(examId, studentId, attemptId);
    }

    /**
     * Submits an exam session for a specific student and calculates the total score based on the provided answers
     * or answers already registered in the session if the session has ended.
     *
     * @param attemptId the unique identifier of the exam attempt for the specific student
     * @param examId the unique identifier of the exam
     * @param studentId the unique identifier of the student attempting the exam
     * @param answers a mapping of question IDs to the corresponding answers provided by the student
     * @throws EntityNotFoundException if the exam session cannot be found based on the provided identifiers
     * @throws IllegalArgumentException if the exam session has already been submitted
     */
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

    /**
     * Updates the student's answers for a given exam session.
     * Processes the provided answers and associates them with the respective questions
     * and session, then stores them in the session's student answers list.
     *
     * @param answers a map where the keys represent question IDs and the values represent the answers
     *                provided by the student
     * @param session the current exam session for which the answers are being updated
     */
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

    /**
     * Saves the exam session by updating the student's answers and persisting the session.
     * Throws exceptions if the session cannot be found, has already been submitted, or if the session has ended.
     *
     * @param attemptId  the attempt identifier representing the specific attempt of the student
     * @param examId     the unique identifier of the exam
     * @param studentId  the unique identifier of the student
     * @param answers    a map containing question IDs as keys and the corresponding student answers as values
     */
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
