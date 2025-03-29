package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing session request details.
 *
 * This class is used to encapsulate the basic information required to
 * initiate or reference a session for an exam, such as the attempt ID
 * and the exam ID.
 */
@Getter
public class SessionRequestDTO {
    private short attemptId;
    private String examId;

    /**
     * Default constructor for the SessionRequestDTO class.
     * Initializes a new instance of the SessionRequestDTO with no pre-set properties or values.
     */
    public SessionRequestDTO() {
    }

    /**
     * Constructs a new SessionRequestDTO with the specified attempt ID and exam ID.
     *
     * @param attemptId the unique ID representing the attempt number within the session
     * @param examId    the unique ID of the exam associated with the session
     */
    public SessionRequestDTO(short attemptId, String examId) {
        this.attemptId = attemptId;
        this.examId = examId;
    }
}
