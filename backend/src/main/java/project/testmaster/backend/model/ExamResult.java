package project.testmaster.backend.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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

    @ManyToOne
    @JoinColumn(name = "exam_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false, nullable = false)
    private Student student;

    public ExamResult() {
    }

    public ExamResult(Exam exam, Student student, float score, String feedback, Timestamp startTime, Timestamp endTime) {
        this.score = score;
        this.feedback = feedback;
        this.startTime = startTime;
        this.endTime = endTime;
        this.exam = exam;
        this.student = student;
    }
}
