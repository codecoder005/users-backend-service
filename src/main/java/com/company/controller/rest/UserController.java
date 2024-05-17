package com.company.controller.rest;

import com.company.dto.UserDto;
import com.company.model.ChangePasswordRequest;
import com.company.model.UpdateUserRequest;
import com.company.service.UserService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.company.common.AppConstants.Names.SECURITY_REQUIREMENT_BEARER_AUTH;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "User Controller",
        description = """
                This UserController class serves as the interface for user-related operations in the backend system.
                It provides endpoints for retrieving, updating, and deleting user information.
                Users can be retrieved individually by their unique identifier or collectively.
                Additionally, it supports updating user details, changing passwords, and deleting user accounts.
                The class ensures data integrity by validating incoming requests against defined constraints.
                Through these endpoints, the UserController facilitates seamless management of user-related tasks within the system, enhancing overall user experience and system security.
                """,
        externalDocs = @ExternalDocumentation(
                url = "https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/"
        )
)
@SecurityRequirement(name = SECURITY_REQUIREMENT_BEARER_AUTH)
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users.", operationId = "getAllUsers")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserDto>> getAllUsers(
            Authentication authentication
    ) {
        log.info("UserController::getAllUsers");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @Operation(summary = "Get user by ID", description = "Retrieve user details by user ID.", operationId = "getUserById")
    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserDto> getUserById(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        log.info("UserController::getUserById");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    @Operation(summary = "Update user", description = "Update user details by user ID.", operationId = "updateUser")
    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest updateUserRequest,
            Authentication authentication
    ) {
        log.info("UserController::updateUser");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(id, updateUserRequest));
    }

    @Operation(summary = "Delete user by ID", description = "Delete user by user ID.", operationId = "deleteUser")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        log.info("UserController::deleteUser");
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Change user password", description = "Change the password for a user by user ID.", operationId = "changePassword")
    @PatchMapping(
            value = "/{id}/change-password",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Void> changePassword(
            @PathVariable UUID id,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest,
            Authentication authentication
    ) {
        log.info("UserController::changePassword");
        userService.changePassword(id, changePasswordRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
