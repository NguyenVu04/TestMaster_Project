package project.testmaster.backend.dto;

public class ExamSubmitRequestDTO {
    private String examId;
    private String studentId;
    private short attemptId;
    private StudentAnswerDTO[] answers;

    public ExamSubmitRequestDTO() {
    }

    public ExamSubmitRequestDTO(String examId, String studentId, short attemptId, StudentAnswerDTO[] answers) {
        this.examId = examId;
        this.studentId = studentId;
        this.attemptId = attemptId;
        this.answers = answers;
    }

    public String getExamId() {
        return examId;
    }

    public String getStudentId() {
        return studentId;
    }

    public short getAttemptId() {
        return attemptId;
    }

    public StudentAnswerDTO[] getAnswers() {
        return answers;
    }
}
