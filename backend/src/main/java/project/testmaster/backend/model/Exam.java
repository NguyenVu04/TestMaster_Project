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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
    private int attemptLimit;

    @Column(name = "passcode")
    private String passcode;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "time_limit")
    private int timeLimit;

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExamResult> examResult;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "exam_question",
        joinColumns = @JoinColumn(name = "exam_id"),
        inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questions;

    public Exam() {
    }

    public Exam(UUID id) {
        this.id = id;
    }

    public Exam(
            Teacher teacher,
            String title,
            String description,
            int attemptLimit,
            String passocode,
            Timestamp startTime,
            Timestamp endTime,
            int timeLimit) {
        this.teacher = teacher;
        this.title = title;
        this.description = description;
        this.attemptLimit = attemptLimit;
        this.passcode = passocode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeLimit = timeLimit;
    }

    public UUID getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public boolean checkPasscode(String passocode) {
        return this.passcode.equals(passocode);
    }

    public String getPassocode() {
        return passcode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAttemptLimit() {
        return attemptLimit;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public List<ExamResult> getExamResult() {
        return Collections.unmodifiableList(this.examResult);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAttemptLimit(int attemptLimit) {
        this.attemptLimit = attemptLimit;
    }

    public void setPassocode(String passocode) {
        this.passcode = passocode;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}
