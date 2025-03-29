package project.testmaster.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import project.testmaster.backend.model.Admin;

/**
 * Repository interface for performing database operations on {@link Admin} entities.
 *
 * This interface extends {@link JpaRepository}, providing methods for standard
 * CRUD (Create, Read, Update, Delete) operations, as well as additional query capabilities
 * for the {@link Admin} entity.
 *
 * The repository uses {@link UUID} as the type for the primary key of {@link Admin}.
 */
public interface AdminRepository extends JpaRepository<Admin, UUID> {
}