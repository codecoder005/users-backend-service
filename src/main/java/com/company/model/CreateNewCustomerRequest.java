package com.company.model;

import com.company.common.AppConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.company.common.AppConstants.ErrorMessage.*;
import static com.company.common.AppConstants.Regex.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNewCustomerRequest {
    @NotEmpty(message = NAME_EMPTY)
    @NotBlank(message = NAME_BLANK)
    private String name;

    @NotEmpty(message = "aadharNo is required")
    @Pattern(regexp = REGEX_AADHAR_NO, message = "invalid aadharNo format")
    private String aadharNo;

    @Pattern(regexp = REGEX_PAN, message = "invalid pan format")
    private String pan;

    private String email;
    private String phoneNo;
}
