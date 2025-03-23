package project.testmaster.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import project.testmaster.backend.model.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

}
