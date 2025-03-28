package project.testmaster.backend.dto;

import lombok.Getter;

@Getter
public class SessionRequestDTO {
    private short attemptId;
    private String examId;

    public SessionRequestDTO() {
    }

    public SessionRequestDTO(short attemptId, String examId) {
        this.attemptId = attemptId;
        this.examId = examId;
    }

}
