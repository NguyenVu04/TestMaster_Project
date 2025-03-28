package project.testmaster.backend.dto;

import lombok.Getter;

@Getter
public class StartExamResponseDTO {
    private short attemptId;
    private String examId;
    private String studentId;

    public StartExamResponseDTO() {
    }

    public StartExamResponseDTO(
            short attemptId,
            String examId,
            String studentId
    ) {
        this.attemptId = attemptId;
        this.examId = examId;
        this.studentId = studentId;
    }

}
