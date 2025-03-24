package project.testmaster.backend.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ExamQuestionId {
    @Column(name = "exam_id", insertable = false, updatable = false)
    private UUID examId;

    @Column(name = "question_id", insertable = false, updatable = false)
    private UUID questionId;

    public ExamQuestionId() {
    }

    public ExamQuestionId(UUID examId, UUID questionId) {
        this.examId = examId;
        this.questionId = questionId;
    }

    public UUID getExamId() {
        return examId;
    }

    public UUID getQuestionId() {
        return questionId;
    }
}
