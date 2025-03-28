package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.testmaster.backend.model.Question;
import project.testmaster.backend.model.Question.QuestionType;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.repository.QuestionRepository;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(
            Teacher teacher,
            QuestionType type,
            String content,
            String[] mediaUrl,
            String[] options,
            String answer) {
        
        Question question = new Question(teacher, type, content, mediaUrl, options, answer);
        return questionRepository.save(question);
    }

    public Question getQuestionById(UUID id) {
        return questionRepository.findById(id).orElse(null);
    }

    public void deleteQuestion(UUID id) {
        questionRepository.deleteById(id);
    }

    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }
}
