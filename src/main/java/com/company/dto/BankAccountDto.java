package com.company.dto;

import com.company.entity.AccountStatus;
import com.company.entity.CustomerEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccountDto {
    private UUID uuid;
    private String ifscCode;
    private BigDecimal availableBalance;
    private BigDecimal balanceOnHold;
    private BigDecimal balanceOnBlocked;

    private AccountStatus accountStatus;
    private CustomerEntity customer;
}
