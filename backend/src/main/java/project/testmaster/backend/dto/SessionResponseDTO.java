package project.testmaster.backend.dto;

public class SessionResponseDTO {
    private SessionQuestionDTO[] questions;
    private long startTime;
    private long endTime;
    private int timeLimit;
    private boolean submitted;

    public SessionResponseDTO() {
    }

    public SessionResponseDTO(SessionQuestionDTO[] questions, long startTime, long endTime, int timeLimit, boolean submitted) {
        this.questions = questions;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeLimit = timeLimit;
        this.submitted = submitted;
    }

    public SessionQuestionDTO[] getQuestions() {
        return questions;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public boolean isSubmitted() {
        return submitted;
    }
}
