package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.StudentAnswer;
import project.testmaster.backend.model.StudentAnswerId;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, StudentAnswerId> {

}
