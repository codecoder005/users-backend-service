package com.company.api;

import com.company.dto.BankAccountDto;
import com.company.model.request.CreateNewBankAccountRequest;
import com.company.model.response.CreateNewBankAccountResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public interface AccountAPI {
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<CreateNewBankAccountResponse> create(
            @Valid @RequestBody CreateNewBankAccountRequest request
    );

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<BankAccountDto>> getAllAccounts();

    @GetMapping(value = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<BankAccountDto> getAccountByUUID(@PathVariable UUID uuid);
}
