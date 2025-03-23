package project.testmaster.backend.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "question_type")
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

    public Question(QuestionType type, String content, String[] mediaUrl, String[] options, String answer) {
        this.type = type;
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
        return new ArrayList<>(Arrays.asList(mediaUrl));
    }

    public List<String> getOptions() {
        return new ArrayList<>(Arrays.asList(options));
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
}
