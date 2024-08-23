package com.company.controller.rest;

import com.company.api.PingAPI;
import com.company.dto.BankAccountDto;
import com.company.model.request.CreateNewBankAccountRequest;
import com.company.model.response.CreateNewBankAccountResponse;
import com.company.model.response.PingAPIResponse;
import com.company.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController implements PingAPI {
    private final AccountService accountService;

    @Override
    public PingAPIResponse ping() {
        log.info("AccountController::ping");
        return PingAPIResponse.builder()
                .status(200)
                .message("Up and Healthy")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateNewBankAccountResponse> create(
            @Valid @RequestBody CreateNewBankAccountRequest request
    ) {
        log.info("AccountController::create");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createNewAccount(request));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<BankAccountDto>> getAllAccounts() {
        log.info("AccountController::getAllAccounts");
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.getAllAccounts());
    }

    @GetMapping(value = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BankAccountDto> getAccountByUUID(@PathVariable UUID uuid) {
        log.info("AccountController::getAccountByUUID");
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.getAccountByUUID(uuid));
    }
}
