package project.testmaster.backend.dto;

public class ExamSubmitRequestDTO {
    private String examId;
    private short attemptId;
    private StudentAnswerDTO[] answers;

    public ExamSubmitRequestDTO() {
    }

    public ExamSubmitRequestDTO(String examId, short attemptId, StudentAnswerDTO[] answers) {
        this.examId = examId;
        this.attemptId = attemptId;
        this.answers = answers;
    }

    public String getExamId() {
        return examId;
    }

    public short getAttemptId() {
        return attemptId;
    }

    public StudentAnswerDTO[] getAnswers() {
        return answers;
    }
}
