package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.User;

/**
 * Data Transfer Object for User Profile information.
 */
@Getter
public class UserProfileDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    /**
     * Default constructor for the UserProfileDTO class.
     * Initializes a new instance of the UserProfileDTO with no pre-set properties or values.
     */
    public UserProfileDTO() {
    }

    /**
     * Constructs a new UserProfileDTO with the specified user details.
     *
     * @param id          the unique identifier of the user
     * @param firstName   the first name of the user
     * @param lastName    the last name of the user
     * @param phoneNumber the phone number of the user
     * @param email       the email address of the user
     */
    public UserProfileDTO(String id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Converts a User entity to a UserProfileDTO.
     *
     * @param user the User entity to convert
     * @return the converted UserProfileDTO
     */
    public static UserProfileDTO fromEntity(User user) {
        return new UserProfileDTO(
            user.getId().toString(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhoneNumber(),
            user.getAccount().getEmail()
        );
    }
}
