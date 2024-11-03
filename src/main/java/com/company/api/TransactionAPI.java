package com.company.api;

import com.company.dto.TransactionDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public interface TransactionAPI {
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<TransactionDto>> getAllTransactions();

    @GetMapping(value = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<TransactionDto> getTransactionById(@PathVariable UUID uuid);

    @GetMapping(value = "/sender/{senderUUID}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<TransactionDto>> getTransactionsBySenderUUID(
            @PathVariable("senderUUID") UUID senderUUID
    );

    @GetMapping(value = "/receiver/{receiverUUID}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<TransactionDto>> getTransactionsByReceiverUUID(
            @PathVariable("receiverUUID") UUID receiverUUID
    );
}
