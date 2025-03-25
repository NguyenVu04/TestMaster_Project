package project.testmaster.backend.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.validation.Valid;
import project.testmaster.backend.dto.UserProfileDTO;
import project.testmaster.backend.model.Account;
import project.testmaster.backend.model.User;
import project.testmaster.backend.service.UserService;

@RestController
@RequestMapping(path = "/api/user")
@SecuritySchemes({
        @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
})
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Get user profile",
            description = "Get user profile",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile", content = @Content(schema = @Schema(implementation = UserProfileDTO.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile() {
        try {

            SecurityContext context = SecurityContextHolder.getContext();
            Account account = (Account) context
                    .getAuthentication()
                    .getPrincipal();

            UUID id = account.getUserId();
            UserProfileDTO userProfile = UserProfileDTO
                    .fromEntity(userService
                            .getUserById(id));

            return ResponseEntity.ok(userProfile);

        } catch (Exception e) {

            logger.error("Error getting user profile", e);
            return ResponseEntity
                    .internalServerError()
                    .build();

        }
    }

    @Operation(
            summary = "Update user profile",
            description = "Update user profile",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile updated", content = @Content(schema = @Schema(implementation = UserProfileDTO.class))),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDTO> updateProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "The user profile to update",
            content = @Content(
                    schema = @Schema(implementation = UserProfileDTO.class)
            )
    )
    @Valid @RequestBody UserProfileDTO request) {
        try {

            SecurityContext context = SecurityContextHolder.getContext();
            Account account = (Account) context
                    .getAuthentication()
                    .getPrincipal();

            UUID id = account.getUserId();
            User user = userService.getUserById(id);

            if (!request.getId().equals(id.toString())) {
                logger.error("User ID does not match");
                return ResponseEntity
                        .badRequest()
                        .build();
            }

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhoneNumber(request.getPhoneNumber());
            user.getAccount().setEmail(request.getEmail());
            user = userService.updateUser(user);

            UserProfileDTO userProfile = UserProfileDTO
                    .fromEntity(user);

            return ResponseEntity.ok(userProfile);

        } catch (Exception e) {

            logger.error("Error updating user profile", e);
            return ResponseEntity
                    .internalServerError()
                    .build();

        }
    }

    @Operation(
            summary = "Delete user profile",
            description = "Delete user profile",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile deleted"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteProfile() {
        try {

            SecurityContext context = SecurityContextHolder.getContext();
            Account account = (Account) context
                    .getAuthentication()
                    .getPrincipal();

            UUID id = account.getUserId();
            userService.deleteUser(id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {

            logger.error("Error deleting user profile", e);
            return ResponseEntity
                    .internalServerError()
                    .build();

        }
    }
}