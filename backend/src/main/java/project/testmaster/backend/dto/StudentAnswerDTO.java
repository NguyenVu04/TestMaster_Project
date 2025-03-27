package project.testmaster.backend.dto;

public class StudentAnswerDTO {
    private String questionId;
    private String answer;

    public StudentAnswerDTO() {
    }

    public StudentAnswerDTO(String questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }
}
