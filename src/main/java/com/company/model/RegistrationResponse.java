package com.company.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationResponse {
    private String status;
    private UUID uid;
    private String email;
    private LocalDateTime registeredOn;
}
