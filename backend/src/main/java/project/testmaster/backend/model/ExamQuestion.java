package project.testmaster.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_question")
public class ExamQuestion {
    @EmbeddedId
    private ExamQuestionId id;

    @ManyToOne
    @JoinColumn(name = "exam_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Question question;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "score", nullable = false)
    private float score;

    @Column(name = "auto_score", nullable = false)
    private boolean autoScore;

    public ExamQuestion() {
    }

    public ExamQuestion(Exam exam, Question question, float score, boolean autoScore, int number) {
        this.id = new ExamQuestionId(exam.getId(), question.getId());
        this.exam = exam;
        this.question = question;
        this.score = score;
        this.autoScore = autoScore;
        this.number = number;
    }

    public ExamQuestionId getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean isAutoScore() {
        return autoScore;
    }

    public void setAutoScore(boolean autoScore) {
        this.autoScore = autoScore;
    }
}
