package com.company.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotEmpty(message = "oldPassword is required")
    private String oldPassword;

    @NotEmpty(message = "newPassword is required")
    @Pattern(
            regexp = "[a-zA-Z0-9]{8,50}",
            message = "password should be 8-50 characters long and should contain alphanumeric characters only"
    )
    private String newPassword;
}
