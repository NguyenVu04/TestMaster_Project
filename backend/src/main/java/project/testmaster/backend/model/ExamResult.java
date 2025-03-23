package project.testmaster.backend.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_student")
public class ExamResult {
    @EmbeddedId
    private ExamResultId id;

    @Column(name = "score")
    private float score;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private Student student;

    public ExamResult() {
    }

    public ExamResult(UUID studentId, UUID examId, float score, String feedback, Timestamp startTime, Timestamp endTime) {
        this.score = score;
        this.feedback = feedback;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = new ExamResultId(studentId, examId);
    }
}
