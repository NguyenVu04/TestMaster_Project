package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) for starting an exam.
 * This class contains the information required to represent the initial response
 * when a student begins an exam, including the attempt ID, the exam ID,
 * and the student ID.
 */
@Getter
public class StartExamResponseDTO {
    private short attemptId;
    private String examId;
    private String studentId;

    /**
     * Default constructor for StartExamResponseDTO.
     * Initializes an instance of the StartExamResponseDTO class without setting any properties.
     */
    public StartExamResponseDTO() {
    }

    /**
     * Constructs a new StartExamResponseDTO with the specified details.
     *
     * @param attemptId the unique ID of the exam attempt
     * @param examId    the unique ID of the exam
     * @param studentId the unique ID of the student starting the exam
     */
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
