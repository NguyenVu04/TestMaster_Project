package project.testmaster.backend.dto;

import lombok.Getter;
import project.testmaster.backend.model.ExamQuestion;
import project.testmaster.backend.model.Question.QuestionType;

import java.util.List;

/**
 * Data Transfer Object for representing a teacher's view of a question within an exam.
 */
@Getter
public class TeacherQuestionViewDTO {
    private int number;
    private float score;
    private boolean autoScore;
    private QuestionType type;
    private String questionId;
    private String content;
    private String answer;
    private String[] options;
    private String[] mediaUrl;

    /**
     * Default constructor for the TeacherQuestionViewDTO class.
     * Initializes a new instance of the TeacherQuestionViewDTO with no pre-set properties or values.
     */
    public TeacherQuestionViewDTO() {
    }

    /**
     * Constructs a new TeacherQuestionViewDTO with the specified details.
     *
     * @param number      the question number in the exam
     * @param score       the score assigned to the question
     * @param autoScore   indicates whether the question can be automatically scored
     * @param type        the type of the question (e.g., MULTIPLE_CHOICE, SHORT_ANSWER)
     * @param questionId  the unique identifier for the question
     * @param content     the content or body of the question
     * @param answer      the correct answer to the question
     * @param options     the list of given options for the question, applicable for multiple-choice questions
     * @param mediaUrl    the list of URLs associated with media resources for the question
     */
    public TeacherQuestionViewDTO(int number, float score, boolean autoScore, QuestionType type, String questionId, String content, String answer, String[] options, String[] mediaUrl) {
        this.number = number;
        this.score = score;
        this.autoScore = autoScore;
        this.type = type;
        this.questionId = questionId;
        this.content = content;
        this.answer = answer;
        this.options = options;
        this.mediaUrl = mediaUrl;
    }

    /**
     * Converts an ExamQuestion entity to a TeacherQuestionViewDTO.
     *
     * @param examQuestion the ExamQuestion entity to convert
     * @return the converted TeacherQuestionViewDTO
     */
    public static TeacherQuestionViewDTO fromEntity(ExamQuestion examQuestion) {
        List<String> options = examQuestion.getQuestion().getOptions();
        List<String> mediaUrl = examQuestion.getQuestion().getMediaUrl();
        return new TeacherQuestionViewDTO(
            examQuestion.getNumber(),
            examQuestion.getScore(),
            examQuestion.isAutoScore(),
            examQuestion.getQuestion().getType(),
            examQuestion.getQuestion().getId().toString(),
            examQuestion.getQuestion().getContent(),
            examQuestion.getQuestion().getAnswer(),
            options != null ? options.toArray(new String[0]) : new String[0],
            mediaUrl != null ? mediaUrl.toArray(new String[0]) : new String[0]
        );
    }
}
