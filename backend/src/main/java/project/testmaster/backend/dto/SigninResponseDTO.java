package project.testmaster.backend.dto;

import lombok.Getter;

@Getter
public class SigninResponseDTO {
    private String accessToken;

    public SigninResponseDTO() {
    }

    public SigninResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

}
