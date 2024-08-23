package com.company.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.company.common.AppConstants.ErrorMessage.NAME_BLANK;
import static com.company.common.AppConstants.ErrorMessage.NAME_REQUIRED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "email is required")
    @Email(message = "invalid email")
    private String email;

    @NotEmpty(message = NAME_REQUIRED)
    @NotBlank(message = NAME_BLANK)
    private String name;

    @NotEmpty(message = "password is required")
    @Pattern(
            regexp = "[a-zA-Z0-9]{8,50}",
            message = "password should be 8-50 characters long and should contain alphanumeric characters only"
    )
    private String password;

    @NotEmpty(message = "roles required")
    @Pattern(
            regexp = "(user|admin)(,(user|admin))?",
            message = "Please delimit the roles by comma and use only 'user' and/or 'admin'"
    )
    private String roles;
}
