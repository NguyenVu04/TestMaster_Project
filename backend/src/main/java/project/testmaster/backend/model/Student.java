package project.testmaster.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student")
public class Student {
    @Getter
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExamSession> examResults;

    public Student() {
    }

    public Student(UUID userId) {
        this.userId = userId;
    }

    public Student(User user) {
        this.user = user;
    }

    public List<ExamSession> getExamResults() {
        return Collections.unmodifiableList(examResults);
    }
}
