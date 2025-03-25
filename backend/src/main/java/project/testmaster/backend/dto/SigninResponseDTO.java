package project.testmaster.backend.dto;

public class SigninResponseDTO {
    private String accessToken;

    public SigninResponseDTO() {
    }

    public SigninResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
