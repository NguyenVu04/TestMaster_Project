package project.testmaster.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Exam> exams;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Question> questions;

    public Teacher() {
    }

    public Teacher(UUID userId) {
        this.userId = userId;
    }

    public Teacher(User user) {
        this.user = user;
    }

    @Transactional
    public List<Exam> getExams() {
        return exams;
    }

    @Transactional
    public List<Question> getQuestions() {
        return questions;
    }
}
