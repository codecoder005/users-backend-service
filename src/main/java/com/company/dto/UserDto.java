package com.company.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID uid;
    private String name;
    private String email;
    private String roles;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
