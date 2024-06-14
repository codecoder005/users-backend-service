package com.company.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNewBankAccountRequest {
    @NotNull(message = "customerId is required")
    private UUID customerId;

    @NotEmpty(message = "ifscCode is required")
    private String ifscCode;

    @NotNull(message = "availableBalance is required")
    @DecimalMin(value = "0.0", message = "opening availableBalance should be a minimum of 0.0")
    private BigDecimal availableBalance;
}
