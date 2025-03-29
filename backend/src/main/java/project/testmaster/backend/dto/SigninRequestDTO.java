package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) for handling user sign-in requests.
 * This class encapsulates the necessary information required for a user
 * to authenticate within the system.
 */
@Getter
public class SigninRequestDTO {
    private String email;
    private String password;

    /**
     * Default constructor for the SigninRequestDTO class.
     * Initializes a new instance of SigninRequestDTO with no pre-set properties or values.
     */
    public SigninRequestDTO() {
    }

    /**
     * Constructs a SigninRequestDTO with the specified email and password.
     *
     * @param email    the email address of the user
     * @param password the password of the user
     */
    public SigninRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
