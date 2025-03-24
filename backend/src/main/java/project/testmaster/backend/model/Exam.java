package project.testmaster.backend.model;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Represents an exam in the system.
 */
@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "user_id")
    private Teacher teacher;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "attempt_limit")
    private short attemptLimit;

    @Column(name = "passcode")
    private String passcode;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "time_limit")
    private int timeLimit;

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExamSession> examResult;

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ExamQuestion> examQuestions;

    /**
     * Default constructor.
     */
    public Exam() {
    }

    /**
     * Constructs a new Exam with the specified ID.
     *
     * @param id the ID of the exam
     */
    public Exam(UUID id) {
        this.id = id;
    }

    /**
     * Constructs a new Exam with the specified details.
     *
     * @param teacher      the teacher associated with the exam
     * @param title        the title of the exam
     * @param description  the description of the exam
     * @param attemptLimit the attempt limit for the exam
     * @param passcode     the passcode for the exam
     * @param startTime    the start time of the exam
     * @param endTime      the end time of the exam
     * @param timeLimit    the time limit for the exam
     */
    public Exam(
            Teacher teacher,
            String title,
            String description,
            short attemptLimit,
            String passcode,
            Timestamp startTime,
            Timestamp endTime,
            int timeLimit) {
        this.teacher = teacher;
        this.title = title;
        this.description = description;
        this.attemptLimit = attemptLimit;
        this.passcode = passcode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeLimit = timeLimit;
    }

    /**
     * Returns the ID of the exam.
     *
     * @return the ID of the exam
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the teacher associated with the exam.
     *
     * @return the teacher associated with the exam
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * Checks if the provided passcode matches the exam's passcode.
     *
     * @param passcode the passcode to check
     * @return true if the passcode matches, false otherwise
     */
    public boolean checkPasscode(String passcode) {
        return this.passcode.equals(passcode);
    }

    /**
     * Returns the passcode of the exam.
     *
     * @return the passcode of the exam
     */
    public String getPasscode() {
        return passcode;
    }

    /**
     * Returns the title of the exam.
     *
     * @return the title of the exam
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of the exam.
     *
     * @return the description of the exam
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the attempt limit for the exam.
     *
     * @return the attempt limit for the exam
     */
    public short getAttemptLimit() {
        return attemptLimit;
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
     * Returns the time limit for the exam.
     *
     * @return the time limit for the exam
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * Returns the list of exam results associated with the exam.
     *
     * @return the list of exam results
     */
    public List<ExamSession> getExamResult() {
        return Collections.unmodifiableList(this.examResult);
    }

    /**
     * Sets the title of the exam.
     *
     * @param title the new title of the exam
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the exam.
     *
     * @param description the new description of the exam
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the attempt limit for the exam.
     *
     * @param attemptLimit the new attempt limit for the exam
     */
    public void setAttemptLimit(short attemptLimit) {
        this.attemptLimit = attemptLimit;
    }

    /**
     * Sets the passcode for the exam.
     *
     * @param passcode the new passcode for the exam
     */
    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    /**
     * Sets the start time of the exam.
     *
     * @param startTime the new start time of the exam
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the exam.
     *
     * @param endTime the new end time of the exam
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets the time limit for the exam.
     *
     * @param timeLimit the new time limit for the exam
     */
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}
