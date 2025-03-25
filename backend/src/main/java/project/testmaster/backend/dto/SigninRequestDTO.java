package project.testmaster.backend.dto;

public class SigninRequestDTO {
    private String email;
    private String password;

    public SigninRequestDTO() {
    }

    public SigninRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
