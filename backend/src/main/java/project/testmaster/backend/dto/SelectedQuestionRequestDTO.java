package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing a selected question in an assessment or quiz.
 * This class is used to encapsulate the relevant information of a selected question, such as its ID,
 * score, whether it is automatically scored, and its position or number in the assessment.
 */
@Getter
public class SelectedQuestionRequestDTO {
    private String questionId;
    private float score;
    private boolean autoScore;
    private int number;

    /**
     * Default constructor for SelectedQuestionRequestDTO.
     * Initializes an instance of the SelectedQuestionRequestDTO class without setting any properties.
     */
    public SelectedQuestionRequestDTO() {
    }

    /**
     * Constructs a SelectedQuestionRequestDTO instance with the specified values.
     *
     * @param questionId the unique identifier of the question
     * @param score      the score assigned to the question
     * @param autoScore  whether the question is automatically scored or not
     * @param number     the position or number of the question in the assessment
     */
    public SelectedQuestionRequestDTO(String questionId, float score, boolean autoScore, int number) {
        this.questionId = questionId;
        this.score = score;
        this.autoScore = autoScore;
        this.number = number;
    }
}
