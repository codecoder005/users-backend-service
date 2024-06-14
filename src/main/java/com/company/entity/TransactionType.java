package com.company.entity;

import lombok.Getter;

@Getter
public enum TransactionType {
    TRANSFER,
    CREDIT,
    DEBIT,
    REFUND
}
