package com.company.model.response;

import com.company.entity.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AmountTransferResponse {
    private UUID uuid;
    private UUID sender;
    private UUID receiver;

    private TransactionStatus status;

    private BigDecimal amount;
}
