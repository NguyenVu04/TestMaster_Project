package project.testmaster.backend.dto;

import lombok.Getter;

/**
 * Data Transfer Object for creating a new exam request.
 */
@Getter
public class NewExamRequestDTO {
    private String title;
    private String description;
    private Short attemptLimit;
    private String passcode;
    private Long startTime;
    private Long endTime;
    private Integer timeLimit;
    private SelectedQuestionRequestDTO[] selectedQuestions;
    private NewQuestionRequestDTO[] newQuestions;

    /**
     * Default constructor for the NewExamRequestDTO class.
     * Initializes a new instance of the NewExamRequestDTO without setting any properties.
     */
    public NewExamRequestDTO() {
    }

    /**
     * Constructs a new instance of NewExamRequestDTO with the provided details.
     *
     * @param title             the title of the exam
     * @param description       the description of the exam
     * @param attemptLimit      the maximum number of attempts allowed for the exam
     * @param passcode          the passcode required to access the exam
     * @param startTime         the start time of the exam in milliseconds
     * @param endTime           the end time of the exam in milliseconds
     * @param timeLimit         the time limit for the exam in minutes
     * @param selectedQuestions an array of existing questions to include in the exam
     * @param newQuestions      an array of new questions to include in the exam
     */
    public NewExamRequestDTO(String title, String description, Short attemptLimit, String passcode, Long startTime, Long endTime, Integer timeLimit, SelectedQuestionRequestDTO[] selectedQuestions, NewQuestionRequestDTO[] newQuestions) {
        this.title = title;
        this.description = description;
        this.attemptLimit = attemptLimit;
        this.passcode = passcode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeLimit = timeLimit;
        this.selectedQuestions = selectedQuestions;
        this.newQuestions = newQuestions;
    }
}
