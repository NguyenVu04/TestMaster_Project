package project.testmaster.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.Exam;

/**
 * ExamRepository is a data access layer interface that extends JpaRepository.
 * It provides methods for performing CRUD operations and interacting with the database
 * for entities of type Exam. The identifier type for the Exam entity is UUID.
 *
 * This interface leverages Spring Data JPA to automatically implement basic
 * repository methods such as saving, finding, deleting, and updating Exam entities.
 */
public interface ExamRepository extends JpaRepository<Exam, UUID> {

}
