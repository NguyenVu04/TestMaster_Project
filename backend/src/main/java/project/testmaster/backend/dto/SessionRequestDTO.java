package project.testmaster.backend.dto;

public class SessionRequestDTO {
    private short attemptId;
    private String examId;

    public SessionRequestDTO() {
    }

    public SessionRequestDTO(short attemptId, String examId) {
        this.attemptId = attemptId;
        this.examId = examId;
    }

    public short getAttemptId() {
        return attemptId;
    }

    public String getExamId() {
        return examId;
    }
}
