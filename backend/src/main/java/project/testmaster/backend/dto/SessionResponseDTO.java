package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) for representing the response of a session.
 * This class encapsulates the details of a session including its questions, timing, and submission status.
 */
@Getter
public class SessionResponseDTO {
    private SessionQuestionDTO[] questions;
    private long startTime;
    private long endTime;
    private int timeLimit;
    private boolean submitted;

    /**
     * Default constructor for the SessionResponseDTO class.
     * Initializes a new instance of SessionResponseDTO with default values for all properties.
     */
    public SessionResponseDTO() {
    }

    /**
     * Constructs a new SessionResponseDTO with the specified details about a session.
     *
     * @param questions an array of SessionQuestionDTO representing the questions in the session
     * @param startTime the start time of the session in milliseconds
     * @param endTime the end time of the session in milliseconds
     * @param timeLimit the time limit for the session in minutes
     * @param submitted a boolean indicating whether the session has been submitted
     */
    public SessionResponseDTO(SessionQuestionDTO[] questions, long startTime, long endTime, int timeLimit, boolean submitted) {
        this.questions = questions;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeLimit = timeLimit;
        this.submitted = submitted;
    }
}
