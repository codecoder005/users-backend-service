package com.company.service;

import com.company.dto.UserDto;
import com.company.model.ChangePasswordRequest;
import com.company.model.UpdateUserRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(UUID id);

    UserDto updateUser(UUID id, UpdateUserRequest updateUserRequest);

    void deleteUser(UUID id);

    void changePassword(UUID id, ChangePasswordRequest request);
}
