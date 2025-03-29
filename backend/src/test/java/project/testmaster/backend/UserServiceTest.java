
package project.testmaster.backend;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.testmaster.backend.repository.UserRepository;
import project.testmaster.backend.model.User;
import project.testmaster.backend.service.AccountService;
import project.testmaster.backend.service.UserService;


@SpringBootTest
public class UserServiceTest {
    @Autowired

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp(){
        userId = UUID.randomUUID();
        user = new User();
        user = new User("John" , "Nguyen" , "0123456781");
        user = new User("Tom" , "Tran" , "0123456782");
        user = new User("Steve" , "Job" , "0123456783");
        user = new User("Alice" , "Smith" , "0123456784");
        
    }

    @Test
    void testGetUserById(){
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(userId);
        assertNotNull(foundUser);
        assertEquals("Bach", foundUser.getFirstName());
    }

    @Test
    void testCreateUser(){
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.createUser("Bach", "Tran", "0123456789", "bach@gmail.com", "bach1234")
        assertNotNull(createdUser);
        verify(accountService, times(1)).createAccount(any(User.class)), eq("bach@gmail.com" , "bach1234");
    }

    @Test
    void testDeleteUser(){
    }
}
