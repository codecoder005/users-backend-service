package com.company.model;

import com.company.common.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest implements Serializable {

    @NotEmpty(message = AppConstants.ErrorMessage.EMAIL_REQUIRED)
    @Email(message = "invalid email")
    private String email;

    @NotEmpty(message = AppConstants.ErrorMessage.PASSWORD_REQUIRED)
    private String password;
}
