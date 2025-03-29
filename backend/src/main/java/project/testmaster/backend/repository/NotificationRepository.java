package project.testmaster.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.Notification;

/**
 * NotificationRepository is a data access layer interface that extends JpaRepository.
 * It provides methods for performing CRUD operations and interacting with the database
 * for entities of type Notification. The identifier type for the Notification entity is UUID.
 *
 * This interface leverages Spring Data JPA to automatically implement basic
 * repository methods such as saving, finding, deleting, and updating Notification entities.
 */
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
