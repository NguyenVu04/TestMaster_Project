package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.Exam;
import project.testmaster.backend.model.ExamSession;
import project.testmaster.backend.model.User;

/**
 * Data Transfer Object (DTO) representing the result of an exam taken by a student.
 * This class is used to encapsulate detailed information regarding an exam attempt and its results.
 *
 * It includes details about the exam, teacher, student, scores, feedback, and the start and end times of the exam.
 */
@Getter
public class ExamResultDTO {
    private Short attemptId;
    private String examId;
    private String examTitle;
    private String examDescription;
    private String examTeacherName;
    private String examTeacherId;
    private String studentName;
    private String studentId;
    private Float totalScore;
    private String feedback;
    private Long startTime;
    private Long endTime;

    /**
     * Default constructor for the ExamResultDTO class.
     * Initializes a new instance of the ExamResultDTO with no pre-set properties or values.
     */
    public ExamResultDTO() {
    }

    /**
     * Constructs a new ExamResultDTO with the specified details.
     *
     * @param attemptId         the unique ID of the attempt
     * @param examId            the unique ID of the exam
     * @param examTitle         the title of the exam
     * @param examDescription   a description of the exam
     * @param examTeacherName   the full name of the teacher who conducted the exam
     * @param examTeacherId     the unique ID of the teacher who conducted the exam
     * @param studentName       the full name of the student who attempted the exam
     * @param studentId         the unique ID of the student who attempted the exam
     * @param totalScore        the total score achieved by the student in the exam
     * @param feedback          the feedback provided for the attempted exam
     * @param startTime         the start time of the exam in milliseconds
     * @param endTime           the end time of the exam in milliseconds
     */
    public ExamResultDTO(
            Short attemptId,
            String examId,
            String examTitle,
            String examDescription,
            String examTeacherName,
            String examTeacherId,
            String studentName,
            String studentId,
            Float totalScore,
            String feedback,
            Long startTime,
            Long endTime) {
        this.attemptId = attemptId;
        this.examId = examId;
        this.examTitle = examTitle;
        this.examDescription = examDescription;
        this.examTeacherName = examTeacherName;
        this.studentName = studentName;
        this.studentId = studentId;
        this.examTeacherId = examTeacherId;
        this.totalScore = totalScore;
        this.feedback = feedback;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts an {@link ExamSession} entity into an {@link ExamResultDTO} object.
     *
     * @param entity the {@link ExamSession} entity containing the details of an exam session
     * @return an {@link ExamResultDTO} object populated with the details from the provided {@link ExamSession} entity
     */
    public static ExamResultDTO fromEntity(ExamSession entity) {
        Exam exam = entity.getExam();
        User teacher = exam.getTeacher().getUser();
        User student = entity.getStudent().getUser();
        return new ExamResultDTO(
                entity.getId().getAttemptId(),
                exam.getId().toString(),
                exam.getTitle(),
                teacher.getFirstName() + " " + teacher.getLastName(),
                teacher.getId().toString(),
                student.getId().toString(),
                student.getFirstName() + " " + student.getLastName(),
                student.getId().toString(),
                entity.getTotalScore(),
                entity.getFeedback(),
                entity.getStartTime().getTime(),
                entity.getEndTime().getTime()
        );
    }
}
