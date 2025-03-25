package project.testmaster.backend.dto;

import java.util.UUID;

import project.testmaster.backend.model.Exam;

/**
 * Data Transfer Object for Exam information.
 */
public class ExamInfoDTO {
    private String title;
    private String description;
    private Short attemptLimit;
    private Long startTime;
    private Long endTime;
    private Integer timeLimit;
    private String teacherFirstName;
    private String teacherLastName;
    private String teacherId;

    /**
     * Default constructor.
     */
    public ExamInfoDTO() {
    }

    /**
     * Constructs a new ExamInfoDTO with the specified details.
     *
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
            String title,
            String description,
            short attemptLimit,
            Long startTime,
            Long endTime,
            Integer timeLimit,
            String teacherFirstName,
            String teacherLastName,
            UUID teacherId) {
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

    /**
     * Returns the title of the exam.
     *
     * @return the title of the exam
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of the exam.
     *
     * @return the description of the exam
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the attempt limit for the exam.
     *
     * @return the attempt limit for the exam
     */
    public Short getAttemptLimit() {
        return attemptLimit;
    }

    /**
     * Returns the start time of the exam in milliseconds.
     *
     * @return the start time of the exam in milliseconds
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the exam in milliseconds.
     *
     * @return the end time of the exam in milliseconds
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * Returns the time limit for the exam.
     *
     * @return the time limit for the exam
     */
    public Integer getTimeLimit() {
        return timeLimit;
    }

    /**
     * Returns the first name of the teacher.
     *
     * @return the first name of the teacher
     */
    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    /**
     * Returns the last name of the teacher.
     *
     * @return the last name of the teacher
     */
    public String getTeacherLastName() {
        return teacherLastName;
    }

    /**
     * Returns the ID of the teacher.
     *
     * @return the ID of the teacher
     */
    public String getTeacherId() {
        return teacherId;
    }
}
