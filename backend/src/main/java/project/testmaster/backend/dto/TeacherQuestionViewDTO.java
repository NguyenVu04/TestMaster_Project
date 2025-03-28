package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.ExamQuestion;
import project.testmaster.backend.model.Question.QuestionType;

import java.util.List;

@Getter
public class TeacherQuestionViewDTO {
    private int number;
    private float score;
    private boolean autoScore;
    private QuestionType type;
    private String questionId;
    private String content;
    private String answer;
    private String[] options;
    private String[] mediaUrl;

    public TeacherQuestionViewDTO() {
    }

    public TeacherQuestionViewDTO(int number, float score, boolean autoScore, QuestionType type, String questionId, String content, String answer, String[] options, String[] mediaUrl) {
        this.number = number;
        this.score = score;
        this.autoScore = autoScore;
        this.type = type;
        this.questionId = questionId;
        this.content = content;
        this.answer = answer;
        this.options = options;
        this.mediaUrl = mediaUrl;
    }

    public static TeacherQuestionViewDTO fromEntity(ExamQuestion examQuestion) {
        List<String> options = examQuestion.getQuestion().getOptions();
        List<String> mediaUrl = examQuestion.getQuestion().getMediaUrl();
        return new TeacherQuestionViewDTO(
            examQuestion.getNumber(),
            examQuestion.getScore(),
            examQuestion.isAutoScore(),
            examQuestion.getQuestion().getType(),
            examQuestion.getQuestion().getId().toString(),
            examQuestion.getQuestion().getContent(),
            examQuestion.getQuestion().getAnswer(),
            options != null ? options.toArray(new String[0]) : new String[0],
            mediaUrl != null ? mediaUrl.toArray(new String[0]) : new String[0]
        );
    }

}
