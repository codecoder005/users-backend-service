package com.company.entity;

import lombok.Getter;

@Getter
public enum KYCStatus {
    INITIATED,
    RE_INITIATED,
    PROCESSING,
    COMPLETED,
    FAILED;
}
