package project.testmaster.backend.dto;

import lombok.Getter;

@Getter
public class StudentAnswerDTO {
    private String questionId;
    private String answer;

    public StudentAnswerDTO() {
    }

    public StudentAnswerDTO(String questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

}
