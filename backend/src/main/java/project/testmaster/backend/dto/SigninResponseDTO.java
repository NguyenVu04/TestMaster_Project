package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) for representing the sign-in response.
 * This class encapsulates the access token returned to the client
 * upon successful authentication and authorization.
 */
@Getter
public class SigninResponseDTO {
    private String accessToken;

    /**
     * Default constructor for the SigninResponseDTO class.
     * Initializes a new instance of the SigninResponseDTO with no pre-set properties or values.
     */
    public SigninResponseDTO() {
    }

    /**
     * Constructs a new SigninResponseDTO with the specified access token.
     *
     * @param accessToken the access token issued upon successful sign-in
     */
    public SigninResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
