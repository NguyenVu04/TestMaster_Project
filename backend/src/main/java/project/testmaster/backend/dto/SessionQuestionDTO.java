package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.Question.QuestionType;

@Getter
public class SessionQuestionDTO {
    private String id;
    private String content;
    private String[] options;
    private QuestionType type;
    private String[] mediaUrl;
    private String currentAnswer;
    private float score;

    public SessionQuestionDTO() {
    }

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
