package project.testmaster.backend.dto;

import project.testmaster.backend.model.ExamQuestion;
import project.testmaster.backend.model.Question.QuestionType;

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
        return new TeacherQuestionViewDTO(
            examQuestion.getNumber(),
            examQuestion.getScore(),
            examQuestion.isAutoScore(),
            examQuestion.getQuestion().getType(),
            examQuestion.getQuestion().getId().toString(),
            examQuestion.getQuestion().getContent(),
            examQuestion.getQuestion().getAnswer(),
            examQuestion.getQuestion().getOptions().toArray(new String[0]),
            examQuestion.getQuestion().getMediaUrl().toArray(new String[0])
        );
    }

    public int getNumber() {
        return number;
    }

    public float getScore() {
        return score;
    }

    public boolean isAutoScore() {
        return autoScore;
    }

    public QuestionType getType() {
        return type;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getContent() {
        return content;
    }

    public String getAnswer() {
        return answer;
    }

    public String[] getOptions() {
        return options;
    }

    public String[] getMediaUrl() {
        return mediaUrl;
    }
}
