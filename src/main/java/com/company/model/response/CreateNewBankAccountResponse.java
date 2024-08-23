package com.company.model.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNewBankAccountResponse {
    private UUID accountId;
    private UUID customerId;
    private String message;
}
