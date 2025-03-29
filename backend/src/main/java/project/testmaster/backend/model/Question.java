package project.testmaster.backend.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "question")
public class Question {
    public enum QuestionType {
        MULTIPLE_CHOICE,
        SHORT_ANSWER
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", referencedColumnName = "user_id")
    private Teacher creator;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "question_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private QuestionType type;

    @Column(name = "content")
    private String content;

    @Column(name = "media_url")
    private String[] mediaUrl;

    @Column(name = "options")
    private String[] options;

    @Column(name = "answer")
    private String answer;

    public Question() {
    }

    public Question(UUID id) {
        this.id = id;
    }

    public Question(Teacher creator, QuestionType type, String content, String[] mediaUrl, String[] options, String answer) {
        this.type = type;
        this.creator = creator;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.options = options;
        this.answer = answer;
    }

    public UUID getId() {
        return id;
    }

    public QuestionType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public List<String> getMediaUrl() {
        return new ArrayList<>(mediaUrl != null ? Arrays.asList(mediaUrl) : new ArrayList<>());
    }

    public List<String> getOptions() {
        return new ArrayList<>(options != null ? Arrays.asList(options) : new ArrayList<>());
    }

    public String getAnswer() {
        return answer;
    }

    public boolean checkAnswer(String answer) {
        return this.answer.equals(answer);
    }

    public String getCorrectAnswer() {
        return answer;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMediaUrl(String[] mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void addMediaUrl(String mediaUrl) {
        List<String> mediaUrlList = new ArrayList<>(Arrays.asList(this.mediaUrl));
        mediaUrlList.add(mediaUrl);
        this.mediaUrl = mediaUrlList.toArray(new String[0]);
    }

    public void removeMediaUrl(String mediaUrl) {
        List<String> mediaUrlList = new ArrayList<>(Arrays.asList(this.mediaUrl));
        mediaUrlList.remove(mediaUrl);
        this.mediaUrl = mediaUrlList.toArray(new String[0]);
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void addOption(String option) {
        List<String> optionsList = new ArrayList<>(Arrays.asList(this.options));
        optionsList.add(option);
        this.options = optionsList.toArray(new String[0]);
    }
    
    public void removeOption(String option) {
        List<String> optionsList = new ArrayList<>(Arrays.asList(this.options));
        optionsList.remove(option);
        this.options = optionsList.toArray(new String[0]);
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Transactional
    public Teacher getCreator() {
        return creator;
    }
}
