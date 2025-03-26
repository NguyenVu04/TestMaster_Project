package project.testmaster.backend.dto;

public class SessionResponseDTO {
    private SessionQuestionDTO[] questions;
    private long startTime;
    private long endTime;
    private int timeLimit;

    public SessionResponseDTO() {
    }

    public SessionResponseDTO(SessionQuestionDTO[] questions, long startTime, long endTime, int timeLimit) {
        this.questions = questions;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeLimit = timeLimit;
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
}
