package com.company.model;

import com.company.node.PlayerRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePlayerRequest {
    @NotNull(message = "name is required")
    @NotBlank(message = "name can not be blank")
    @NotEmpty(message = "name is empty")
    private String name;

    @NotNull(message = "Date is required and must be in yyyy-MM-dd format")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private List<PlayerRole> roles;

    @NotNull(message = "height is required")
    @Positive(message = "height should be positive")
    @DecimalMin(value = "160.00", message = "minimum height is 160")
    private Float height;

    @NotNull(message = "weight is required")
    @Positive(message = "weight should be positive")
    @DecimalMin(value = "60.0")
    private Float weight;

    @NotNull(message = "country is required")
    @NotBlank(message = "country can not be blank")
    @NotEmpty(message = "country is empty")
    private String country;

    @NotNull(message = "state is required")
    @NotBlank(message = "state can not be blank")
    @NotEmpty(message = "state is empty")
    private String state;
}
