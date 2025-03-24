package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.ExamSession;
import project.testmaster.backend.model.ExamSessionId;

public interface ExamResultRepository extends JpaRepository<ExamSession, ExamSessionId> {

}
