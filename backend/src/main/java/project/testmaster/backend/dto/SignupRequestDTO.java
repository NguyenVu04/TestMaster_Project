package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object for signup requests.
 */
@Getter
public class SignupRequestDTO {
    /**
     * -- GETTER --
     *  Returns the email of the signup request.
     *
     */
    private String email;
    /**
     * -- GETTER --
     *  Returns the password of the signup request.
     *
     */
    private String password;
    /**
     * -- GETTER --
     *  Returns the first name of the signup request.
     *
     */
    private String firstName;
    /**
     * -- GETTER --
     *  Returns the last name of the signup request.
     *
     */
    private String lastName;
    /**
     * -- GETTER --
     *  Returns the phone number of the signup request.
     *
     */
    private String phoneNumber;

    public SignupRequestDTO() {}

    public SignupRequestDTO(String email, String password, String firstName, String lastName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}