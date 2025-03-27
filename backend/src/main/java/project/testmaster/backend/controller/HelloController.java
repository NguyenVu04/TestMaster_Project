package project.testmaster.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

/**
 * HelloController is a REST controller that handles HTTP GET requests to the root URL ("/").
 * It returns a simple HTML greeting message.
 */
@SecuritySchemes({
    @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
})
@RestController
public class HelloController {
    final Logger logger = LoggerFactory.getLogger(HelloController.class);

    /**
     * Handles HTTP GET requests to the root URL ("/").
     *
     * @return A string containing an HTML snippet with a greeting message.
     */
    @Operation(summary = "Greet the user", description = "Returns a simple HTML greeting message.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping("/api")
    public String hello() {
        return "<h1>Hello, World!</h1>";
    }
}