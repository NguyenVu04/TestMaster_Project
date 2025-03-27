package project.testmaster.backend.dto;

import project.testmaster.backend.model.Exam;

public class TeacherExamViewDTO {
    private String examId;
    private String title;
    private String description;
    private String passcode;
    private long startTime;
    private long endTime;
    private TeacherQuestionViewDTO[] questions;
    private int timeLimit;

    public TeacherExamViewDTO() {
    }

    public TeacherExamViewDTO(String examId, String title, String description, String passcode, long startTime,
            long endTime, TeacherQuestionViewDTO[] questions, int timeLimit) {
        this.examId = examId;
        this.title = title;
        this.description = description;
        this.passcode = passcode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questions = questions;
        this.timeLimit = timeLimit;
    }

    public static TeacherExamViewDTO fromEntity(Exam exam) {
        return new TeacherExamViewDTO(
                exam.getId().toString(),
                exam.getTitle(),
                exam.getDescription(),
                exam.getPasscode(),
                exam.getStartTime().getTime(),
                exam.getEndTime().getTime(),
                exam.getExamQuestions()
                        .stream()
                        .map(TeacherQuestionViewDTO::fromEntity)
                        .toArray(TeacherQuestionViewDTO[]::new),
                exam.getTimeLimit());
    }

    public String getExamId() {
        return examId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPasscode() {
        return passcode;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public TeacherQuestionViewDTO[] getQuestions() {
        return questions;
    }

    public int getTimeLimit() {
        return timeLimit;
    }
}
