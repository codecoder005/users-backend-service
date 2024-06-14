package com.company.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "accounts")
public class BankAccountEntity extends BaseEntity {
    private String ifscCode;
    @Column(nullable = false)
    private BigDecimal availableBalance;
    @Column(nullable = false)
    private BigDecimal balanceOnHold;
    @Column(nullable = false)
    private BigDecimal balanceOnBlocked;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private CustomerEntity customer;
}
