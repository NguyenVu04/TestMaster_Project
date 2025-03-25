package project.testmaster.backend.model;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Represents the result of an exam taken by a student.
 */
@Entity
@Table(name = "exam_student")
public class ExamSession {
    @EmbeddedId
    private ExamSessionId id;

    @Column(name = "total_score")
    private Float totalScore;

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

    @OneToMany(mappedBy = "examResult", fetch = FetchType.LAZY)
    private List<StudentAnswer> studentAnswers;

    /**
     * Default constructor.
     */
    public ExamSession() {
    }

    /**
     * Constructs a new ExamResult with the specified details.
     *
     * @param exam      the exam associated with this result
     * @param student   the student who took the exam
     * @param feedback  the feedback for the student
     * @param startTime the start time of the exam
     * @param endTime   the end time of the exam
     */
    public ExamSession(
            Exam exam,
            Student student,
            String feedback,
            Timestamp startTime,
            Timestamp endTime) {
        this.exam = exam;
        this.student = student;
        this.feedback = feedback;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the ID of this exam result.
     *
     * @return the ID of this exam result
     */
    public ExamSessionId getId() {
        return id;
    }

    /**
     * Returns the total score obtained by the student.
     *
     * @return the total score obtained by the student
     */
    public float getTotalScore() {
        return totalScore;
    }

    /**
     * Returns the feedback for the student.
     *
     * @return the feedback for the student
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Returns the start time of the exam.
     *
     * @return the start time of the exam
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the exam.
     *
     * @return the end time of the exam
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * Returns the exam associated with this result.
     *
     * @return the exam associated with this result
     */
    public Exam getExam() {
        return exam;
    }

    /**
     * Returns the student who took the exam.
     *
     * @return the student who took the exam
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Returns the list of student answers associated with this exam result.
     *
     * @return the list of student answers
     */
    public List<StudentAnswer> getStudentAnswers() {
        return Collections.unmodifiableList(studentAnswers);
    }
}
