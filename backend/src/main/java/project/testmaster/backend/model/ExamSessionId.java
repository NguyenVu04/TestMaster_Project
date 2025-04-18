package project.testmaster.backend.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ExamSessionId {
    @Column(name = "attempt_id")
    private short attemptId;
    
    @Column(name = "student_id", insertable = false, updatable = false)
    private UUID studentId;
    
    @Column(name = "exam_id", insertable = false, updatable = false)
    private UUID examId;

    public ExamSessionId() {
    }

    public ExamSessionId(short attemptId, UUID studentId, UUID examId) {
        this.attemptId = attemptId;
        this.studentId = studentId;
        this.examId = examId;
    }

    public short getAttemptId() {
        return attemptId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public UUID getExamId() {
        return examId;
    }
}
