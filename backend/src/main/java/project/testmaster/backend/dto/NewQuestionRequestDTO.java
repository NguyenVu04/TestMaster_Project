package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.Question.QuestionType;

/**
 * Data Transfer Object (DTO) for creating a new question in a system.
 * This class is used to encapsulate all necessary properties needed to define a question,
 * including metadata, content, scoring details, and any*/
@Getter
public class NewQuestionRequestDTO {
    private int number;
    private float score;
    private boolean autoScore;
    private QuestionType type;
    private String content;
    private String[] mediaUrl;
    private String[] options;
    private String answer;

    /**
     * Default constructor for the NewQuestionRequestDTO class.
     * Initializes a new instance of the NewQuestionRequestDTO with no pre-set properties or values.
     */
    public NewQuestionRequestDTO() {
    }

    /**
     * Constructs a new instance of {@code NewQuestionRequestDTO} with the specified parameters.
     *
     * @param number    the sequence number of the question
     * @param score     the score assigned to the question
     * @param autoScore a flag indicating if the question should be automatically scored
     * @param type      the type of the question (e.g., MULTIPLE_CHOICE, SHORT_ANSWER)
     * @param content   the textual content of the question
     * @param mediaUrl  an array of URLs pointing to media resources related to the question
     * @param options   an array of possible answer options for the question
     * @param answer    the correct answer for the question
     */
    public NewQuestionRequestDTO(int number, float score, boolean autoScore, QuestionType type, String content, String[] mediaUrl, String[] options, String answer) {
        this.number = number;
        this.score = score;
        this.autoScore = autoScore;
        this.type = type;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.options = options;
        this.answer = answer;
    }
}