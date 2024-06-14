package com.company.controller.rest;

import com.company.model.*;
import com.company.service.BankingService;
import com.flagsmith.exceptions.FlagsmithClientError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/banking")
@RequiredArgsConstructor
@Slf4j
public class BankingController {
    private final BankingService bankingService;

    @PostMapping(
            value = "/transfer",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<AmountTransferResponse> transfer(
            @Valid @RequestBody AmountTransferRequest request
    ) throws FlagsmithClientError
    {
        log.info("BankingController::transfer");
        return ResponseEntity.status(HttpStatus.OK)
                .body(bankingService.transfer(request));
    }

    @PostMapping(value = "/deposit", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DepositResponse> deposit(@Valid @RequestBody DepositRequest request) throws FlagsmithClientError {
        log.info("BankingController::deposit");
        return ResponseEntity.status(HttpStatus.OK)
                .body(bankingService.deposit(request));
    }

    @PostMapping(value = "/withdraw", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WithdrawResponse> withdraw(@Valid @RequestBody WithdrawRequest request) {
        log.info("BankingController::withdraw");
        return ResponseEntity.status(HttpStatus.OK)
                .body(bankingService.withdraw(request));
    }
}
