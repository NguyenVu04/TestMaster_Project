package project.testmaster.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project.testmaster.backend.model.ExamSession;
import project.testmaster.backend.model.ExamSessionId;

public interface ExamSessionRepository extends JpaRepository<ExamSession, ExamSessionId> {
    @Query("SELECT es FROM ExamSession es WHERE es.id.examId = ?1 AND es.id.studentId = ?2 AND es.id.attemptId = ?3")
    public ExamSession findByExamIdAndStudentIdAndAttemptId(UUID examId, UUID studentId, short attemptId);

    @Query("DELETE FROM ExamSession es WHERE es.id.examId = ?1 AND es.id.studentId = ?2 AND es.id.attemptId = ?3")
    public void deleteByExamIdAndStudentIdAndAttemptId(UUID examId, UUID studentId, short attemptId);
}
