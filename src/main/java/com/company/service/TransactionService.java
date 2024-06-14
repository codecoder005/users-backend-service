package com.company.service;

import com.company.dto.TransactionDto;
import com.company.exception.TransactionNotFoundException;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    List<TransactionDto> getAllTransactions();

    TransactionDto getTransactionByUUID(UUID transactionId) throws TransactionNotFoundException;

    List<TransactionDto> getTransactionsBySenderUUID(UUID senderUUID);
    List<TransactionDto> getTransactionsByReceiverUUID(UUID receiverUUID);
}
