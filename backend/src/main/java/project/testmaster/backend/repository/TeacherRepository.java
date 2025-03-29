package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.testmaster.backend.model.Teacher;

import java.util.UUID;

/**
 * Repository interface for managing {@link Teacher} entities.
 *
 * This interface provides methods to perform CRUD (Create, Read, Update, Delete) operations
 * and interacts directly with the database for {@link Teacher} entity management.
 *
 * By extending the {@link JpaRepository} interface, this repository inherits common
 * persistence methods while also enabling customization for more sophisticated queries.
 *
 * The primary key for the {@link Teacher} entity is of type {@link UUID}.
 */
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}