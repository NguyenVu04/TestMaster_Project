package project.testmaster.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.testmaster.backend.model.Question;
import project.testmaster.backend.model.Question.QuestionType;
import project.testmaster.backend.model.Teacher;
import project.testmaster.backend.repository.QuestionRepository;

/**
 * Service class for managing operations related to Question entities.
 *
 * This service encapsulates business logic for creating, retrieving, updating,
 * and deleting questions. It interacts with the underlying persistence layer
 * through the {@link QuestionRepository} to perform these operations.
 *
 * It supports the following functionalities:
 * - Creating a new question with associated metadata
 * - Retrieving a question by its unique identifier
 * - Deleting a question by its unique identifier
 * - Updating an existing question
 *
 * The service leverages dependency injection to interact with the {@link QuestionRepository}.
 */
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    /**
     * Constructs a new instance of the QuestionService with the provided {@link QuestionRepository}.
     *
     * The {@code QuestionRepository} is used as a dependency to perform CRUD operations
     * on the Question entity. This constructor leverages dependency injection to
     * obtain the required repository implementation.
     *
     * @param questionRepository the repository used for question persistence operations
     */
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Creates a new question and stores it in the repository.
     *
     * @param teacher the teacher who is creating the question
     * @param type the type of the question (e.g., multiple choice, short answer)
     * @param content the content or body of the question
     * @param mediaUrl array of URLs linking to associated media for the question
     * @param options array of possible options for the question if applicable
     * @param answer the correct answer to the question
     * @return the created and saved Question object
     */
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

    /**
     * Retrieves a Question by its unique identifier.
     *
     * @param id the unique identifier of the Question to retrieve
     * @return the Question associated with the specified ID, or null if no Question is found
     */
    public Question getQuestionById(UUID id) {
        return questionRepository.findById(id).orElse(null);
    }

    /**
     * Deletes a question with the specified unique identifier from the repository.
     *
     * @param id the unique identifier of the question to be deleted
     */
    public void deleteQuestion(UUID id) {
        questionRepository.deleteById(id);
    }

    /**
     * Updates an existing question in the database.
     *
     * @param question the Question object containing updated data to be persisted
     * @return the updated Question object after saving in the database
     */
    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }
}
