package project.testmaster.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import project.testmaster.backend.model.Question.QuestionType;

@Getter
public class NewQuestionRequestDTO {
    @NotNull
    private int number;
    @NotNull
    private float score;
    @NotNull
    private boolean autoScore;
    @Setter
    @NotNull
    private QuestionType type;
    @NotNull
    private String content;
    @NotNull
    private String[] mediaUrl;
    @NotNull
    private String[] options;
    @NotNull
    private String answer;

    public NewQuestionRequestDTO() {
    }

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
