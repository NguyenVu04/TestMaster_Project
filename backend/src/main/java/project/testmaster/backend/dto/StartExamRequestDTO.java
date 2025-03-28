package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) for initiating the start of an exam.
 * This class is used to encapsulate the information required to start an exam session.
 */
@Getter
public class StartExamRequestDTO {
    private String passcode;
    private String examId;

    /**
     * Default constructor for the StartExamRequestDTO class.
     * Initializes a new instance of the StartExamRequestDTO with no pre-set properties.
     */
    public StartExamRequestDTO() {
    }

    /**
     * Constructs a new StartExamRequestDTO with the specified passcode and exam ID.
     *
     * @param passcode the passcode required to start the exam
     * @param examId   the unique identifier of the exam to be started
     */
    public StartExamRequestDTO(String passcode, String examId) {
        this.passcode = passcode;
        this.examId = examId;
    }
}
