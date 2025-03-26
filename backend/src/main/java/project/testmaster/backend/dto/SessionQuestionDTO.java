package project.testmaster.backend.dto;

import project.testmaster.backend.model.Question.QuestionType;

public class SessionQuestionDTO {
    private String id;
    private String content;
    private String[] options;
    private String answer;
    private QuestionType type;
    private String[] mediaUrl;
    private String currentAnswer;
    private float score;

    public SessionQuestionDTO() {
    }

    public SessionQuestionDTO(String id, String content, String[] options, String answer, QuestionType type, String[] mediaUrl, String currentAnswer, float score) {
        this.id = id;
        this.content = content;
        this.options = options;
        this.answer = answer;
        this.type = type;
        this.mediaUrl = mediaUrl;
        this.currentAnswer = currentAnswer;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String[] getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionType getType() {
        return type;
    }

    public String[] getMediaUrl() {
        return mediaUrl;
    }

    public String getCurrentAnswer() {
        return currentAnswer;
    }

    public float getScore() {
        return score;
    }
}
