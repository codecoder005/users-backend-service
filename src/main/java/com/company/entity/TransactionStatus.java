package com.company.entity;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PROCESSING,
    SUCCESS,
    FAILURE
}
