package com.company.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest implements Serializable {
    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be blank")
    @NotEmpty(message = "name can not be empty")
    private String name;

    @NotEmpty(message = "email is required")
    @Email(message = "invalid email")
    private String email;

    @NotEmpty(message = "roles required")
    @Pattern(
            regexp = "(user|admin)(,(user|admin))?",
            message = "Please delimit the roles by comma and use only 'user' and/or 'admin'"
    )
    private String roles;
}
