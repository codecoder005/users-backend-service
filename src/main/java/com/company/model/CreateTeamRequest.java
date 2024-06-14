package com.company.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTeamRequest {
    @NotNull(message = "indicator is required")
    @NotBlank(message = "indicator can not be blank")
    @NotEmpty(message = "indicator is empty")
    private String indicator;

    @NotNull(message = "name is required")
    @NotBlank(message = "name can not be blank")
    @NotEmpty(message = "name is empty")
    private String name;

    @NotNull(message = "homeGround is required")
    @NotBlank(message = "homeGround can not be blank")
    @NotEmpty(message = "homeGround is empty")
    private String homeGround;
}
