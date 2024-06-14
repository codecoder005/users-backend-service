package com.company.config;

import lombok.Getter;

@Getter
public enum AppFeatureFlags {
    TRANSFER_ENABLED("transfer_enabled"),
    DEPOSIT_ENABLED("deposit_enabled"),
    WITHDRAW_ENABLED("withdraw_enabled"),

    IMPS_ENABLED("imps_enabled"),
    NEFT_ENABLED("neft_enabled"),
    RTGS_ENABLED("rtgs_enabled");

    private final String name;

    AppFeatureFlags(String name) {
        this.name = name;
    }
}
