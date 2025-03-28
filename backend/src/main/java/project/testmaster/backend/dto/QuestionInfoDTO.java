package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.Question;

@Getter
public class QuestionInfoDTO {
    private String id;
    private Question.QuestionType type;
    private String content;
    private String[] options;
    private String[] mediaUrl;
    private String answer;

    public QuestionInfoDTO() {}

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
