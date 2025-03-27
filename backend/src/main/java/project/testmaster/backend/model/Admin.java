package project.testmaster.backend.model;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Represents an admin in the system.
 */
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Default constructor.
     */
    public Admin() {
    }

    /**
     * Constructs a new Admin with the specified user.
     *
     * @param user the user associated with this admin
     */
    public Admin(User user) {
        this.user = user;
    }

    /**
     * Returns the user ID of this admin.
     *
     * @return the user ID of this admin
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Returns the user associated with this admin.
     *
     * @return the user associated with this admin
     */
    public User getUser() {
        return user;
    }
}