package project.testmaster.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The BackendApplication class serves as the main entry point for the Spring Boot application.
 * It utilizes the @SpringBootApplication annotation to mark this class as a configuration
 * class and to enable component scanning and auto-configuration.
 */
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
