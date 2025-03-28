package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.User;

@Getter
public class UserProfileDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public UserProfileDTO() {
    }

    public UserProfileDTO(String id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

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
