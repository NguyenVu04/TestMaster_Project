package project.testmaster.backend.dto;

import java.util.UUID;

import lombok.Getter;
import project.testmaster.backend.model.Exam;

/**
 * Data Transfer Object for Exam information.
 */
@Getter
public class ExamInfoDTO {
    /**
     * -- GETTER --
     *  Returns the ID of the exam.
     *
     */
    private String id;
    /**
     * -- GETTER --
     *  Returns the title of the exam.
     *
     */
    private String title;
    /**
     * -- GETTER --
     *  Returns the description of the exam.
     *
     */
    private String description;
    /**
     * -- GETTER --
     *  Returns the attempt limit for the exam.
     *
     */
    private Short attemptLimit;
    /**
     * -- GETTER --
     *  Returns the start time of the exam in milliseconds.
     *
     */
    private Long startTime;
    /**
     * -- GETTER --
     *  Returns the end time of the exam in milliseconds.
     *
     */
    private Long endTime;
    /**
     * -- GETTER --
     *  Returns the time limit for the exam.
     *
     */
    private Integer timeLimit;
    /**
     * -- GETTER --
     *  Returns the first name of the teacher.
     *
     */
    private String teacherFirstName;
    /**
     * -- GETTER --
     *  Returns the last name of the teacher.
     *
     */
    private String teacherLastName;
    /**
     * -- GETTER --
     *  Returns the ID of the teacher.
     *
     */
    private String teacherId;

    /**
     * Default constructor.
     */
    public ExamInfoDTO() {
    }

    /**
     * Constructs a new ExamInfoDTO with the specified details.
     *
     * @param id               the ID of the exam
     * @param title            the title of the exam
     * @param description      the description of the exam
     * @param attemptLimit     the attempt limit for the exam
     * @param startTime        the start time of the exam in milliseconds
     * @param endTime          the end time of the exam in milliseconds
     * @param timeLimit        the time limit for the exam
     * @param teacherFirstName the first name of the teacher
     * @param teacherLastName  the last name of the teacher
     * @param teacherId        the ID of the teacher
     */
    public ExamInfoDTO(
            String id,
            String title,
            String description,
            short attemptLimit,
            Long startTime,
            Long endTime,
            Integer timeLimit,
            String teacherFirstName,
            String teacherLastName,
            UUID teacherId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.attemptLimit = attemptLimit;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeLimit = timeLimit;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.teacherId = teacherId.toString();
    }

    /**
     * Converts an Exam entity to an ExamInfoDTO.
     *
     * @param exam the Exam entity to convert
     * @return the converted ExamInfoDTO
     */
    public static ExamInfoDTO fromEntity(Exam exam) {
        return new ExamInfoDTO(
                exam.getId().toString(),
                exam.getTitle(),
                exam.getDescription(),
                exam.getAttemptLimit(),
                exam.getStartTime().getTime(),
                exam.getEndTime().getTime(),
                exam.getTimeLimit(),
                exam.getTeacher().getUser().getFirstName(),
                exam.getTeacher().getUser().getLastName(),
                exam.getTeacher().getUserId());
    }

}
