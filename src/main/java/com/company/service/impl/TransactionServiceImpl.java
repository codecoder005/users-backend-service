package com.company.service.impl;

import com.company.dto.TransactionDto;
import com.company.entity.TransactionEntity;
import com.company.exception.TransactionNotFoundException;
import com.company.repository.TransactionRepository;
import com.company.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TransactionDto> getAllTransactions() {
        log.info("TransactionServiceImpl::getAllTransactions");
        return transactionRepository.findAll().stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .toList();
    }

    @Override
    public TransactionDto getTransactionByUUID(UUID transactionId) throws TransactionNotFoundException {
        log.info("TransactionServiceImpl::getTransactionByUUID");
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("No transaction found with transactionId: " + transactionId));
        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public List<TransactionDto> getTransactionsBySenderUUID(UUID senderUUID) {
        log.info("TransactionServiceImpl::getTransactionsBySenderUUID");
        List<TransactionEntity> transactions = transactionRepository.findBySender(senderUUID).orElseGet(Collections::emptyList);
        return transactions.stream()
                .map(txn -> modelMapper.map(txn, TransactionDto.class))
                .toList();
    }

    @Override
    public List<TransactionDto> getTransactionsByReceiverUUID(UUID receiverUUID) {
        log.info("TransactionServiceImpl::getTransactionsByReceiverUUID");
        List<TransactionEntity> transactions = transactionRepository.findByReceiver(receiverUUID).orElseGet(Collections::emptyList);
        return transactions.stream()
                .map(txn -> modelMapper.map(txn, TransactionDto.class))
                .toList();
    }

}
