package project.testmaster.backend.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class StudentAnswerId {
    @Column(name = "attempt_id", insertable = false, updatable = false)
    private short attemptId;

    @Column(name = "student_id", insertable = false, updatable = false)
    private UUID studentId;

    @Column(name = "question_id", insertable = false, updatable = false)
    private UUID questionId;

    @Column(name = "exam_id", insertable = false, updatable = false)
    private UUID examId;

    public StudentAnswerId() {
    }

    public StudentAnswerId(short attemptId, UUID studentId, UUID questionId, UUID examId) {
        this.attemptId = attemptId;
        this.studentId = studentId;
        this.questionId = questionId;
        this.examId = examId;
    }

    public short getAttemptId() {
        return attemptId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public UUID getExamId() {
        return examId;
    }
}
