package com.company.model;

import com.company.entity.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositResponse {
    private UUID accountUUID;
    private BigDecimal depositAmount;
    private BigDecimal availableBalance;
    private TransactionStatus status;
}
