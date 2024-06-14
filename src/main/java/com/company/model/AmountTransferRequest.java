package com.company.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AmountTransferRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3003112329744478495L;

    @NotNull(message = "from is required")
    private UUID from;

    @NotNull(message = "to is required")
    private UUID to;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "1.0", message = "minimum transfer amount should be 1.0")
    private BigDecimal amount;


    private ElectronicMoneyTransferSystem type;
}
