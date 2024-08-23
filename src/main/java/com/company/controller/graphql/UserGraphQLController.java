package com.company.controller.graphql;

import com.company.dto.UserDto;
import com.company.model.request.UpdateUserRequest;
import com.company.model.UserMutationOperations;
import com.company.model.UserQueryOperations;
import com.company.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserGraphQLController {
    private final UserService userService;

    @QueryMapping(name = "users")
    public UserQueryOperations usersQuery() {
        log.info("UserGraphQLController::usersQuery");
        return new UserQueryOperations();
    }

    @MutationMapping(name = "users")
    public UserMutationOperations usersMutation() {
        log.info("UserGraphQLController::usersMutation");
        return new UserMutationOperations();
    }

    @SchemaMapping(typeName = "UserQueryOperations", field = "get")
    public List<UserDto> get() {
        log.info("UserGraphQLController::get");
        return userService.getAllUsers();
    }
    @SchemaMapping(typeName = "UserQueryOperations", field = "getById")
    public UserDto getById(@Argument UUID uid) {
        log.info("UserGraphQLController::getById");
        return userService.getUserById(uid);
    }

    /**
     * ALL MUTATION OPERATIONS
     */
    @SchemaMapping(typeName = "UserMutationOperations", field = "update")
    public UserDto update(
            @Argument(name = "uid") UUID uid,
            @Valid @Argument(name = "update") UpdateUserRequest updateUserRequest) {
        log.info("UserGraphQLController::Mutation::update");
        return userService.updateUser(uid, updateUserRequest);
    }

    @SchemaMapping(typeName = "UserMutationOperations", field = "delete")
    public Boolean delete(@Argument(name = "uid") UUID uid) {
        log.info("UserGraphQLController::Mutation::delete");
        userService.deleteUser(uid);
        return true;
    }
}
