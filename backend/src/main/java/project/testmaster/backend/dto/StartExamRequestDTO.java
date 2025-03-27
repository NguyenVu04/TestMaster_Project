package project.testmaster.backend.dto;

public class StartExamRequestDTO {
    private String passcode;
    private String examId;

    public StartExamRequestDTO() {
    }

    public StartExamRequestDTO(String passcode, String examId) {
        this.passcode = passcode;
        this.examId = examId;
    }

    public String getPasscode() {
        return passcode;
    }

    public String getExamId() {
        return examId;
    }
}
