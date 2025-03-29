package project.testmaster.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.Question;

/**
 * Repository interface for managing {@link Question} entities.
 * Provides CRUD operations and query methods for accessing and manipulating Question data.
 *
 * Extends the {@link JpaRepository} interface to inherit base repository methods.
 * The repository utilizes {@link UUID} as the ID type for {@link Question} entities.
 */
public interface QuestionRepository extends JpaRepository<Question, UUID> {

}
