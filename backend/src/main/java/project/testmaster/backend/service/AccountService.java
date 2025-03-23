package project.testmaster.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.User;
import project.testmaster.backend.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account createAccount(User user, String email, String password) {
        Account account = new Account(
                user,
                email,
                passwordEncoder
                        .encode(password));
        return accountRepository.save(account);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElse(null);
    }
}
