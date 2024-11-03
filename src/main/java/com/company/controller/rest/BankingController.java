package com.company.controller.rest;

import com.company.api.BankingAPI;
import com.company.api.PingAPI;
import com.company.model.request.AmountTransferRequest;
import com.company.model.request.DepositRequest;
import com.company.model.request.WithdrawRequest;
import com.company.model.response.AmountTransferResponse;
import com.company.model.response.DepositResponse;
import com.company.model.response.PingAPIResponse;
import com.company.model.response.WithdrawResponse;
import com.company.service.BankingService;
import com.flagsmith.exceptions.FlagsmithClientError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class BankingController implements PingAPI, BankingAPI {
    private final BankingService bankingService;

    @Override
    public PingAPIResponse ping() {
        log.info("BankingController::ping");
        return PingAPIResponse.builder()
                .status(200)
                .message("Up and Healthy")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ResponseEntity<AmountTransferResponse> transfer(
            @Valid @RequestBody AmountTransferRequest request
    ) throws FlagsmithClientError
    {
        log.info("BankingController::transfer");
        return ResponseEntity.status(HttpStatus.OK)
                .body(bankingService.transfer(request));
    }

    @Override
    public ResponseEntity<DepositResponse> deposit(@Valid @RequestBody DepositRequest request) throws FlagsmithClientError {
        log.info("BankingController::deposit");
        return ResponseEntity.status(HttpStatus.OK)
                .body(bankingService.deposit(request));
    }

    @Override
    public ResponseEntity<WithdrawResponse> withdraw(@Valid @RequestBody WithdrawRequest request) {
        log.info("BankingController::withdraw");
        return ResponseEntity.status(HttpStatus.OK)
                .body(bankingService.withdraw(request));
    }
}