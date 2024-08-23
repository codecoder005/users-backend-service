package com.company.service.impl;

import com.company.config.AppFeatureFlags;
import com.company.entity.BankAccountEntity;
import com.company.entity.TransactionEntity;
import com.company.entity.TransactionStatus;
import com.company.entity.TransactionType;
import com.company.exception.AccountNotFoundException;
import com.company.exception.FeatureDisabledException;
import com.company.exception.InsufficientBalanceException;
import com.company.model.*;
import com.company.model.request.AmountTransferRequest;
import com.company.model.request.DepositRequest;
import com.company.model.request.RefundRequest;
import com.company.model.request.WithdrawRequest;
import com.company.model.response.AmountTransferResponse;
import com.company.model.response.DepositResponse;
import com.company.model.response.RefundResponse;
import com.company.model.response.WithdrawResponse;
import com.company.repository.BankAccountRepository;
import com.company.repository.TransactionRepository;
import com.company.service.BankingService;
import com.company.service.EmailService;
import com.flagsmith.FlagsmithClient;
import com.flagsmith.exceptions.FlagsmithClientError;
import com.flagsmith.models.Flags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.company.common.AppConstants.Values.SYSTEM_DEFAULT_EMAIL_ADDRESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankingServiceImpl implements BankingService {
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final FlagsmithClient flagsmithClient;
    private final EmailService emailService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public AmountTransferResponse transfer(AmountTransferRequest request)
            throws InsufficientBalanceException, AccountNotFoundException, FlagsmithClientError
    {
        Flags fsFeatureFlags = flagsmithClient.getEnvironmentFlags();
        if(!fsFeatureFlags.isFeatureEnabled(AppFeatureFlags.TRANSFER_ENABLED.getName())) {
            throw new FeatureDisabledException("Transfer feature is temporarily disabled. Please try after sometime");
        }

        if(request.getType().equals(ElectronicMoneyTransferSystem.IMPS)) {
            if(!fsFeatureFlags.isFeatureEnabled(AppFeatureFlags.IMPS_ENABLED.getName())) {
                throw new FeatureDisabledException("IMPS transfer is temporarily disabled. Please try after sometime");
            }
        }else if(request.getType().equals(ElectronicMoneyTransferSystem.NEFT)) {
            if(!fsFeatureFlags.isFeatureEnabled(AppFeatureFlags.NEFT_ENABLED.getName())) {
                throw new FeatureDisabledException("NEFT transfer is temporarily disabled. Please try after sometime");
            }
        }else if(request.getType().equals(ElectronicMoneyTransferSystem.RTGS)) {
            if(!fsFeatureFlags.isFeatureEnabled(AppFeatureFlags.RTGS_ENABLED.getName())) {
                throw new FeatureDisabledException("NEFT transfer is temporarily disabled. Please try after sometime");
            }
        }

        BankAccountEntity sender = bankAccountRepository.findById(request.getFrom())
                .orElseThrow(() -> new AccountNotFoundException("No account found with accountId: " + request.getFrom()));
        BankAccountEntity receiver = bankAccountRepository.findById(request.getTo())
                .orElseThrow(() -> new AccountNotFoundException("No account found with accountId: " + request.getTo()));

        if(sender.getAvailableBalance().compareTo(request.getAmount()) >= 0) {
            sender.setAvailableBalance(sender.getAvailableBalance().subtract(request.getAmount()));
            receiver.setAvailableBalance(receiver.getAvailableBalance().add(request.getAmount()));
            TransactionEntity transaction = transactionRepository.save(TransactionEntity.builder()
                            .sender(request.getFrom())
                            .receiver(request.getTo())
                            .amount(request.getAmount())
                            .type(TransactionType.TRANSFER)
                            .status(TransactionStatus.SUCCESS)
                    .build());
            emailService.send(
                    SYSTEM_DEFAULT_EMAIL_ADDRESS,
                    "to@company.com",
                    "Amount Transfer Request Details",
                    """
                    <!DOCTYPE html>
                    <html>
                        <head>
                            <title>Email Notification</title>
                        </head>
                        <body>
                            <h2>Transfer Request</h2>
                        </body>
                    </html>
                    """,
                    true
            );
            return AmountTransferResponse.builder()
                    .uuid(transaction.getUuid())
                    .sender(request.getFrom())
                    .receiver(request.getTo())
                    .amount(request.getAmount())
                    .status(transaction.getStatus())
                    .build();
        }
        throw new InsufficientBalanceException("Insufficient balance in the account");
    }

    @Override
    public DepositResponse deposit(DepositRequest request) throws AccountNotFoundException, FlagsmithClientError {
        Flags fsFeatureFlags = flagsmithClient.getEnvironmentFlags();
        if(!fsFeatureFlags.isFeatureEnabled(AppFeatureFlags.DEPOSIT_ENABLED.getName())) {
            throw new FeatureDisabledException("Deposit feature is temporarily disabled. Please try after sometime");
        }

        BankAccountEntity account = bankAccountRepository.findById(request.getAccountUUID())
                .orElseThrow(() -> new AccountNotFoundException("No account found with accountId: " + request.getAccountUUID()));
        account.setAvailableBalance(account.getAvailableBalance().add(request.getAmount()));

        TransactionEntity transaction = transactionRepository.save(
                TransactionEntity.builder()
                        .sender(account.getUuid())
                        .receiver(account.getUuid())
                        .amount(request.getAmount())
                        .type(TransactionType.CREDIT)
                        .status(TransactionStatus.SUCCESS)
                        .build()
        );

        return DepositResponse.builder()
                .accountUUID(account.getUuid())
                .depositAmount(request.getAmount())
                .availableBalance(account.getAvailableBalance())
                .status(transaction.getStatus())
                .build();
    }

    @Override
    public WithdrawResponse withdraw(WithdrawRequest request) throws AccountNotFoundException, InsufficientBalanceException {

        return null;
    }

    @Override
    public RefundResponse refund(RefundRequest request) throws AccountNotFoundException, InsufficientBalanceException {
        return null;
    }
}
