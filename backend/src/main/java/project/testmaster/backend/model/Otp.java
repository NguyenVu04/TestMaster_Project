package project.testmaster.backend.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "otp")
public class Otp {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "otp")
    private String otp;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "expired_at")
    private Timestamp expiredAt;

    public Otp() {
    }

    public Otp(User user, String otp, Timestamp expiredAt) {
        this.user = user;
        this.otp = otp;
        this.createdAt = Timestamp.from(Instant.now());
        this.expiredAt = expiredAt;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getOtp() {
        return otp;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getExpiredAt() {
        return expiredAt;
    }
}
