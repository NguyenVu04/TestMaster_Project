package project.testmaster.backend.model;

import java.util.UUID;

import jakarta.persistence.*;

/**
 * Represents an account in the system.
 */
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Constructs a new Account.
     */
    public Account() {
    }

    /**
     * Constructs a new Account with the specified user, email, and password.
     *
     * @param user     the user associated with this account
     * @param email    the email of the account
     * @param password the password of the account
     */
    public Account(User user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the email of this account.
     *
     * @return the email of this account
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of this account.
     *
     * @param email the new email of this account
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of this account.
     *
     * @return the password of this account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of this account.
     *
     * @param password the new password of this account
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user associated with this account.
     *
     * @return the user associated with this account
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the user ID of this account.
     *
     * @return the user ID of this account
     */
    public UUID getUserId() {
        return userId;
    }
}
