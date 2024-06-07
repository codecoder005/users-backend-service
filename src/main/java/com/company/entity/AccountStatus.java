package com.company.entity;

import lombok.Getter;

@Getter
public enum AccountStatus {
    CREATED,
    ACTIVE,
    HOLD,
    SUSPENDED,
    CLOSED,
    BLOCKED
}
