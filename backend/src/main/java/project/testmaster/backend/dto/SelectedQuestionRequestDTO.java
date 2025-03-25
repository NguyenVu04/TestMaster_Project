package project.testmaster.backend.dto;

public class SelectedQuestionRequestDTO {
    private String questionId;
    private float score;
    private boolean autoScore;
    private int number;

    public SelectedQuestionRequestDTO() {
    }

    public SelectedQuestionRequestDTO(String questionId, float score, boolean autoScore, int number) {
        this.questionId = questionId;
        this.score = score;
        this.autoScore = autoScore;
        this.number = number;
    }

    public String getQuestionId() {
        return questionId;
    }

    public float getScore() {
        return score;
    }

    public boolean isAutoScore() {
        return autoScore;
    }

    public int getNumber() {
        return number;
    }
}
