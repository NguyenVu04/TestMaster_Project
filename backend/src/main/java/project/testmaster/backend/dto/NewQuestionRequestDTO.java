package project.testmaster.backend.dto;

import project.testmaster.backend.model.Question.QuestionType;

public class NewQuestionRequestDTO {
    private int number;
    private float score;
    private boolean autoScore;
    private QuestionType type;
    private String content;
    private String[] mediaUrl;
    private String[] options;
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
    public void setType(QuestionType type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String[] getMediaUrl() {
        return mediaUrl;
    }
    public void setMediaUrl(String[] mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
    public String[] getOptions() {
        return options;
    }
    public void setOptions(String[] options) {
        this.options = options;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
