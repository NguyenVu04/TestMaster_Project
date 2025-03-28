package project.testmaster.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import project.testmaster.backend.model.User;

/**
 * Repository interface for managing {@link User} entities.
 *
 * This interface extends {@link JpaRepository}, providing built-in methods for
 * performing CRUD (Create, Read, Update, Delete) operations, and additional
 * query capabilities for the {@link User} entity.
 *
 * The repository utilizes {@link UUID} as the type for the primary key of {@link User}.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
}