package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.ExamResult;
import project.testmaster.backend.model.ExamResultId;

public interface ExamResultRepository extends JpaRepository<ExamResult, ExamResultId> {

}
