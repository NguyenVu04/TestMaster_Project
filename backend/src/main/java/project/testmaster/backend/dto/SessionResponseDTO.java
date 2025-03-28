package project.testmaster.backend.dto;

import lombok.Getter;

@Getter
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

}
