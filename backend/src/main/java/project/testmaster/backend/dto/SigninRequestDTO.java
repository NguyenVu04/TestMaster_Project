package project.testmaster.backend.dto;

import lombok.Getter;

@Getter
public class SigninRequestDTO {
    private String email;
    private String password;

    public SigninRequestDTO() {
    }

    public SigninRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
