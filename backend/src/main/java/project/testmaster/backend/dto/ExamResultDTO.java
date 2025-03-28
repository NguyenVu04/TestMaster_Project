package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.Exam;
import project.testmaster.backend.model.ExamSession;
import project.testmaster.backend.model.User;

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

    public ExamResultDTO() {
    }

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
