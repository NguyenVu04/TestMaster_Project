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

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExamResult> examResults;

    public Student() {
    }

    public Student(User user) {
        this.user = user;
    }

    public UUID getUserId() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public List<ExamResult> getExamResults() {
        return Collections.unmodifiableList(examResults);
    }
}
