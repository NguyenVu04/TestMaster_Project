package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.testmaster.backend.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, String> {

}
