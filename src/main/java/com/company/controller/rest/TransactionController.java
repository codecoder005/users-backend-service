package com.company.controller.rest;

import com.company.api.PingAPI;
import com.company.dto.TransactionDto;
import com.company.model.response.PingAPIResponse;
import com.company.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController implements PingAPI {
    private final TransactionService transactionService;

    @Override
    public PingAPIResponse ping() {
        log.info("TransactionController::ping");
        return PingAPIResponse.builder()
                .status(200)
                .message("Up and Healthy")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        log.info("TransactionController::getAllTransactions");
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getAllTransactions());
    }

    @GetMapping(value = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable UUID uuid) {
        log.info("TransactionController::getTransactionById");
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getTransactionByUUID(uuid));
    }

    @GetMapping(value = "/sender/{senderUUID}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TransactionDto>> getTransactionsBySenderUUID(
            @PathVariable("senderUUID") UUID senderUUID
    ) {
        log.info("TransactionController::getTransactionsBySenderUUID");
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getTransactionsBySenderUUID(senderUUID));
    }

    @GetMapping(value = "/receiver/{receiverUUID}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TransactionDto>> getTransactionsByReceiverUUID(
            @PathVariable("receiverUUID") UUID receiverUUID
    ) {
        log.info("TransactionController::getTransactionsByReceiverUUID");
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getTransactionsBySenderUUID(receiverUUID));
    }
}
