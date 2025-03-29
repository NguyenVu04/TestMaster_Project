package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.Question.QuestionType;

/**
 * Data Transfer Object (DTO) representing a session question within an exam or quiz session.
 * This class is used to encapsulate the details of a single question, such as its content,
 * type, possible options, associated media, and the current answer provided by the participant.
 *
 * The class is useful for transferring question-related data between the backend and frontend layers
 * or for processing exam session workflows.
 */
@Getter
public class SessionQuestionDTO {
    private String id;
    private String content;
    private String[] options;
    private QuestionType type;
    private String[] mediaUrl;
    private String currentAnswer;
    private float score;

    /**
     * Default constructor for the SessionQuestionDTO class.
     * Initializes a new instance of the SessionQuestionDTO with no pre-set properties or values.
     */
    public SessionQuestionDTO() {
    }

    /**
     * Constructs a new SessionQuestionDTO with the specified details.
     *
     * @param id            the unique identifier of the question
     * @param content       the textual content of the question
     * @param options       the array of possible answer options for the question
     * @param type          the type of the question (e.g., MULTIPLE_CHOICE, SHORT_ANSWER)
     * @param mediaUrl      the array of URLs to any media associated with the question
     * @param currentAnswer the current answer provided for the question
     * @param score         the score assigned to the question
     */
    public SessionQuestionDTO(String id, String content, String[] options, QuestionType type, String[] mediaUrl, String currentAnswer, float score) {
        this.id = id;
        this.content = content;
        this.options = options;
        this.type = type;
        this.mediaUrl = mediaUrl;
        this.currentAnswer = currentAnswer;
        this.score = score;
    }
}
