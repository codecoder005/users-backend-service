package com.company.service.impl;

import com.company.dto.UserDto;
import com.company.entity.UserEntity;
import com.company.exception.AppException;
import com.company.exception.UserNotFoundException;
import com.company.model.request.ChangePasswordRequest;
import com.company.model.request.UpdateUserRequest;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("UserService::getAllUsers");
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto getUserById(UUID id) {
        log.info("UserService::getUserById {}", id);
        AtomicReference<UserEntity> userEntity = new AtomicReference<>();
        userRepository.findById(id).ifPresentOrElse(
                userEntity::set,
                () -> {
                    throw new UserNotFoundException("no user found with id " + id);
                }
        );
        return modelMapper.map(userEntity.get(), UserDto.class);
    }

    @Override
    public UserDto updateUser(UUID id, UpdateUserRequest updateUserRequest) {
        log.info("UserService::updateUser {}", id);
        AtomicReference<UserDto> userDto = new AtomicReference<>();
        userRepository.findById(id).ifPresentOrElse(
                currentUser -> {
                    currentUser.setName(updateUserRequest.getName());
                    currentUser.setEmail(updateUserRequest.getEmail());
                    currentUser.setRoles(updateUserRequest.getRoles());
                    userDto.set(modelMapper.map(userRepository.save(currentUser), UserDto.class));
                },
                () -> {
                    throw new UserNotFoundException("Unable to update. No user found with id " + id);
                }
        );
        return userDto.get();
    }

    @Override
    public void deleteUser(UUID id) {
        log.info("UserService::deleteUser {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public void changePassword(UUID id, ChangePasswordRequest request) {
        log.info("UserService::changePassword {}", id);
        userRepository.findById(id).ifPresentOrElse(
                user -> {
                    if(!user.getPassword().equals(request.getOldPassword())) {
                        throw new AppException("old password is wrong");
                    }
                    user.setPassword(request.getNewPassword());
                    userRepository.save(user);
                },
                () -> {
                    throw new UserNotFoundException("no user found with id " + id);
                }
        );
    }
}
