package project.testmaster.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
