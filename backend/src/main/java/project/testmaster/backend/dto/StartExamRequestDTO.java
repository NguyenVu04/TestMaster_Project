package project.testmaster.backend.dto;

import lombok.Getter;

@Getter
public class StartExamRequestDTO {
    private String passcode;
    private String examId;

    public StartExamRequestDTO() {
    }

    public StartExamRequestDTO(String passcode, String examId) {
        this.passcode = passcode;
        this.examId = examId;
    }

}
