package project.testmaster.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, UUID> {

}
