package com.company.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawRequest {
    @NotNull(message = "accountUUID is required")
    private UUID accountUUID;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "1.0", message = "minimum withdraw amount should be 1.0")
    private BigDecimal amount;
}
