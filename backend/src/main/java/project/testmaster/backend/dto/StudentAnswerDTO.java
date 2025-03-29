package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing a student's answer to a specific question.
 * This class is used to encapsulate the question ID and the corresponding answer provided by a student.
 */
@Getter
public class StudentAnswerDTO {
    private String questionId;
    private String answer;

    /**
     * Default constructor for the StudentAnswerDTO class.
     * Initializes a new instance of this class with no pre-set properties or values.
     */
    public StudentAnswerDTO() {
    }

    /**
     * Constructs a new StudentAnswerDTO with the specified question ID and answer.
     *
     * @param questionId the ID of the question being answered
     * @param answer     the answer provided for the specified question
     */
    public StudentAnswerDTO(String questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }
}
