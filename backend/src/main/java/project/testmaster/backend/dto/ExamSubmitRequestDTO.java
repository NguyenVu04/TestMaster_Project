package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing the request to submit an exam attempt.
 * This class is primarily used for transferring the necessary data during the submission
 * of a student's answers for a specific exam attempt.
 *
 * It includes details such as the exam ID, the attempt ID, and an array of answers.
 */
@Getter
public class ExamSubmitRequestDTO {
    private String examId;
    private short attemptId;
    private StudentAnswerDTO[] answers;

    /**
     * Default constructor for the ExamSubmitRequestDTO class.
     * Initializes a new instance of the ExamSubmitRequestDTO
     * with default property values for use in exam submission requests.
     */
    public ExamSubmitRequestDTO() {
    }

    /**
     * Constructs a new ExamSubmitRequestDTO with the specified exam submission details.
     *
     * @param examId   the unique identifier of the exam being submitted
     * @param attemptId the attempt number associated with the student's exam submission
     * @param answers  an array of StudentAnswerDTO objects representing the student's answers to the exam questions
     */
    public ExamSubmitRequestDTO(String examId, short attemptId, StudentAnswerDTO[] answers) {
        this.examId = examId;
        this.attemptId = attemptId;
        this.answers = answers;
    }

}
