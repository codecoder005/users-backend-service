package com.company.dto;

import com.company.entity.TransactionStatus;
import com.company.entity.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private UUID uuid;
    private UUID sender;
    private UUID receiver;

    private TransactionType type;

    private TransactionStatus status;

    private BigDecimal amount;

    private LocalDateTime createdOn;
    private UUID createdBy;

    private LocalDateTime updatedOn;
    private UUID updatedBy;
}
