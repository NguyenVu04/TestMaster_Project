package project.testmaster.backend;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	// @Mock
    // private UserService userService;
    
    // @Mock
    // private AuthService authService;

    // @InjectMocks
    // private AuthService loginService;

    // private Account validAccount;
    
    // @BeforeEach
    // void setUp() {
    //     MockitoAnnotations.openMocks(this);
    //     validAccount = new Account();
    //     validAccount.setEmail("test@example.com");
    //     validAccount.setPassword("hashed_password");
    // }

    // @Test
    // void testLoginWithValidCredentials() {
    //     when(authService.authenticate("test@example.com", "password")).thenReturn(validAccount);

    //     Account result = authService.authenticate("test@example.com", "password");
    //     assertNotNull(result);
    //     assertEquals("test@example.com", result.getEmail());
    // }

    // @Test
    // void testLoginWithInvalidCredentials() {
    //     when(authService.authenticate("wrong@example.com", "wrongpass")).thenReturn(null);

    //     Account result = authService.authenticate("wrong@example.com", "wrongpass");
    //     assertNull(result);
    // }

}
