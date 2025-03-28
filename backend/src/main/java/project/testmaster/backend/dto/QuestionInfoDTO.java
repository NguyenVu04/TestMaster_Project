package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.Question;

/**
 * Data Transfer Object for Question information.
 */
@Getter
public class QuestionInfoDTO {
    private String id;
    private Question.QuestionType type;
    private String content;
    private String[] options;
    private String[] mediaUrl;
    private String answer;

    /**
     * Default constructor for the QuestionInfoDTO class.
     * Initializes a new instance of the QuestionInfoDTO with no pre-set properties or values.
     */
    public QuestionInfoDTO() {}

    /**
     * Constructs a new QuestionInfoDTO with the specified details.
     *
     * @param id        the unique identifier of the question
     * @param type      the type of the question (e.g., MULTIPLE_CHOICE, SHORT_ANSWER)
     * @param content   the textual content or description of the question
     * @param options   the possible answer options for the question
     * @param mediaUrl  the URLs of media resources (e.g., images, videos) associated with the question
     * @param answer    the correct answer for the question
     */
    public QuestionInfoDTO(
            String id,
            Question.QuestionType type,
            String content,
            String[] options,
            String[] mediaUrl,
            String answer
    ) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.options = options;
        this.mediaUrl = mediaUrl;
        this.answer = answer;
    }

    /**
     * Converts a Question entity into a QuestionInfoDTO.
     *
     * @param question the Question entity to convert
     * @return a QuestionInfoDTO containing the data from the provided Question entity
     */
    public static QuestionInfoDTO fromEntity(Question question) {
        return new QuestionInfoDTO(
            question.getId().toString(),
            question.getType(),
            question.getContent(),
            question.getOptions().toArray(new String[0]),
            question.getMediaUrl().toArray(new String[0]),
            question.getAnswer()
        );
    }
}
