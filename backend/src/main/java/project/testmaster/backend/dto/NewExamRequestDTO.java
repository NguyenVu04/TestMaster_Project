package project.testmaster.backend.dto;

public class NewExamRequestDTO {
    private String title;
    private String description;
    private Short attemptLimit;
    private String passCode;
    private Long startTime;
    private Long endTime;
    private Integer timeLimit;
    private SelectedQuestionRequestDTO[] selectedQuestions;
    private NewQuestionRequestDTO[] newQuestions;

    public NewExamRequestDTO() {
    }

    public NewExamRequestDTO(String title, String description, Short attemptLimit, String passCode, Long startTime, Long endTime, Integer timeLimit, SelectedQuestionRequestDTO[] selectedQuestions, NewQuestionRequestDTO[] newQuestions) {
        this.title = title;
        this.description = description;
        this.attemptLimit = attemptLimit;
        this.passCode = passCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeLimit = timeLimit;
        this.selectedQuestions = selectedQuestions;
        this.newQuestions = newQuestions;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Short getAttemptLimit() {
        return attemptLimit;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public String getPasscode() {
        return passCode;
    }

    public SelectedQuestionRequestDTO[] getSelectedQuestions() {
        return selectedQuestions;
    }

    public NewQuestionRequestDTO[] getNewQuestions() {
        return newQuestions;
    }
}
