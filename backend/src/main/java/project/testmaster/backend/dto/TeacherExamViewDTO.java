package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.Exam;

/**
 * Data Transfer Object for representing a teacher's view of an exam.
 * This class encapsulates all necessary details about an exam for the teacher's perspective,
 * including metadata, timing, a list of questions, and other relevant information.
 */
@Getter
public class TeacherExamViewDTO {
    private String examId;
    private String title;
    private String description;
    private String passcode;
    private long startTime;
    private long endTime;
    private TeacherQuestionViewDTO[] questions;
    private int timeLimit;

    /**
     * Default constructor for the TeacherExamViewDTO class.
     * Initializes a new instance of TeacherExamViewDTO with no pre-set properties or values.
     */
    public TeacherExamViewDTO() {
    }

    /**
     * Constructs a new instance of TeacherExamViewDTO with specified exam details.
     *
     * @param examId    the unique identifier of the exam
     * @param title     the title of the exam
     * @param description a brief description of the exam
     * @param passcode  the access passcode for the exam
     * @param startTime the start time of the exam in milliseconds
     * @param endTime   the end time of the exam in milliseconds
     * @param questions an array of questions included in the exam
     * @param timeLimit the time limit for completing the exam in seconds
     */
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
}
