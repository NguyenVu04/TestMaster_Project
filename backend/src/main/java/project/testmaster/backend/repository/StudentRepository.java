package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.testmaster.backend.model.Student;

import java.util.UUID;

/**
 * Repository interface for managing {@link Student} entities.
 * Provides methods to perform CRUD (Create, Read, Update, Delete) operations for the Student entity.
 *
 * This interface extends {@link JpaRepository}, leveraging Spring Data JPA's functionality
 * to simplify database interactions for entities with a primary key type of {@link UUID}.
 *
 * The primary purpose of this repository is to serve as a data access layer for
 * managing and querying {@link Student} entities in the underlying database.
 */
public interface StudentRepository extends JpaRepository<Student, UUID> {
}