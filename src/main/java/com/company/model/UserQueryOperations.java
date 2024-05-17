package com.company.model;

import com.company.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQueryOperations {
    private List<UserEntity> get;
    private UserEntity getById;
    private UserEntity post;
}
