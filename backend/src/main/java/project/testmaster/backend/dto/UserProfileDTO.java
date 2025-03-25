package project.testmaster.backend.dto;

import project.testmaster.backend.model.User;

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

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
