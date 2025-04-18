package project.testmaster.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_student_answer")
public class StudentAnswer {
    @EmbeddedId
    private StudentAnswerId id;

    @Column(name = "answer")
    private String answer;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "attempt_id", referencedColumnName = "attempt_id", insertable = false, updatable = false),
            @JoinColumn(name = "student_id", referencedColumnName = "student_id", insertable = false, updatable = false),
            @JoinColumn(name = "exam_id", referencedColumnName = "exam_id", insertable = false, updatable = false)
    })
    private ExamSession examSession;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Question question;

    public StudentAnswer() {
    }

    public StudentAnswer(ExamSession examSession, Question question, String answer) {
        this.id = new StudentAnswerId(
                examSession.getId().getAttemptId(),
                examSession.getId().getStudentId(),
                question.getId(),
                examSession.getId().getExamId());
        this.examSession = examSession;
        this.question = question;
        this.answer = answer;
    }

    public StudentAnswerId getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public ExamSession getExamSession() {
        return examSession;
    }

    public Question getQuestion() {
        return question;
    }
}
