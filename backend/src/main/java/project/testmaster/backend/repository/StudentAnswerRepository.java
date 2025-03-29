package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.StudentAnswer;
import project.testmaster.backend.model.StudentAnswerId;

/**
 * Repository interface for managing {@link StudentAnswer} entities.
 *
 * This interface provides methods for performing CRUD (Create, Read, Update, Delete)
 * operations on the StudentAnswer entity. It extends {@link JpaRepository}, enabling
 * interaction with the database and implementation of common query operations.
 *
 * The entity {@link StudentAnswer} uses a composite key represented by {@link StudentAnswerId}.
 *
 * Usage of this repository leverages Spring Data JPA, allowing automatic
 * implementation of commonly used repository methods.
 */
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, StudentAnswerId> {

}
