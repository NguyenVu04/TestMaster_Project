package project.testmaster.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.testmaster.backend.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    public Optional<Account> findByEmail(String email);
}