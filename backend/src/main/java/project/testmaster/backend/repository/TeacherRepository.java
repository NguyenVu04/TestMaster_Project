package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.testmaster.backend.model.Teacher;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}