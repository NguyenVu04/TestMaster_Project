package project.testmaster.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.testmaster.backend.model.ExamSession;
import project.testmaster.backend.model.ExamSessionId;

/**
 * Repository interface for managing ExamSession entities.
 * Provides methods to perform operations on the ExamSession table in the database.
 */
public interface ExamSessionRepository extends JpaRepository<ExamSession, ExamSessionId> {
    /**
     * Retrieves an ExamSession entity based on the specified exam ID, student ID, and attempt ID.
     *
     * @param examId the unique identifier of the exam
     * @param studentId the unique identifier of the student
     * @param attemptId the attempt number for the exam
     * @return the ExamSession that matches the given criteria, or null if no match is found
     */
    @Query("SELECT es FROM ExamSession es WHERE es.id.examId = ?1 AND es.id.studentId = ?2 AND es.id.attemptId = ?3")
    ExamSession findByExamIdAndStudentIdAndAttemptId(UUID examId, UUID studentId, short attemptId);

    /**
     * Retrieves a list of ExamSession entities based on the specified exam ID and student ID,
     * ordered by the attempt ID.
     *
     * @param examId the unique identifier for the exam
     * @param studentId the unique identifier for the student
     * @return a list of ExamSession entities matching the provided exam ID and student ID,
     *         ordered by the attempt ID
     */
    @Query("SELECT es FROM ExamSession es WHERE es.id.examId = ?1 AND es.id.studentId = ?2 ORDER BY es.id.attemptId")
    List<ExamSession> findByExamIdAndStudentId(UUID examId, UUID studentId);

    /**
     * Deletes an ExamSession entry from the database for a specific exam, student, and attempt.
     *
     * @param examId the unique identifier of the exam
     * @param studentId the unique identifier of the student
     * @param attemptId the attempt number for the exam session
     */
    @Modifying
    @Query("DELETE FROM ExamSession es WHERE es.id.examId = ?1 AND es.id.studentId = ?2 AND es.id.attemptId = ?3")
    void deleteByExamIdAndStudentIdAndAttemptId(UUID examId, UUID studentId, short attemptId);
}
