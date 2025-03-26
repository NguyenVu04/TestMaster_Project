package project.testmaster.backend.dto;

import project.testmaster.backend.model.ExamSessionId;

public class StartExamResponseDTO {
    private ExamSessionId sessionId;

    public StartExamResponseDTO() {
    }

    public StartExamResponseDTO(ExamSessionId sessionId) {
        this.sessionId = sessionId;
    }

    public ExamSessionId getSessionId() {
        return sessionId;
    }
}
