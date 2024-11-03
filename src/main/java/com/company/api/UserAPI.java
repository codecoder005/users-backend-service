package com.company.api;

import com.company.dto.UserDto;
import com.company.model.request.ChangePasswordRequest;
import com.company.model.request.UpdateUserRequest;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.company.common.AppConstants.Names.SECURITY_REQUIREMENT_BEARER_AUTH;

@RestController
@RequestMapping("/api/v1/users")
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
public interface UserAPI {
    @Operation(summary = "Get all users", description = "Retrieve a list of all users.", operationId = "getAllUsers")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<UserDto>> getAllUsers(
            Authentication authentication
    );

    @Operation(summary = "Get user by ID", description = "Retrieve user details by user ID.", operationId = "getUserById")
    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<UserDto> getUserById(
            @PathVariable UUID id,
            Authentication authentication
    );

    @Operation(summary = "Update user", description = "Update user details by user ID.", operationId = "updateUser")
    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<UserDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest updateUserRequest,
            Authentication authentication
    );

    @Operation(summary = "Delete user by ID", description = "Delete user by user ID.", operationId = "deleteUser")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(
            @PathVariable UUID id,
            Authentication authentication
    );

    @Operation(summary = "Change user password", description = "Change the password for a user by user ID.", operationId = "changePassword")
    @PatchMapping(
            value = "/{id}/change-password",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<Void> changePassword(
            @PathVariable UUID id,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest,
            Authentication authentication
    );
}
