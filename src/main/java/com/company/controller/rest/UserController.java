package com.company.controller.rest;

import com.company.api.PingAPI;
import com.company.api.UserAPI;
import com.company.dto.UserDto;
import com.company.model.request.ChangePasswordRequest;
import com.company.model.request.UpdateUserRequest;
import com.company.model.response.PingAPIResponse;
import com.company.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserController implements PingAPI, UserAPI {
    private final UserService userService;

    @Override
    public PingAPIResponse ping() {
        log.info("UserController::ping");
        return PingAPIResponse.builder()
                .status(200)
                .message("Up and Healthy")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllUsers(
            Authentication authentication
    ) {
        log.info("UserController::getAllUsers");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<UserDto> getUserById(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        log.info("UserController::getUserById");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest updateUserRequest,
            Authentication authentication
    ) {
        log.info("UserController::updateUser");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(id, updateUserRequest));
    }

    @Override
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        log.info("UserController::deleteUser");
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
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
