package com.company.service;

import com.company.exception.AccountNotFoundException;
import com.company.exception.InsufficientBalanceException;
import com.company.model.request.AmountTransferRequest;
import com.company.model.request.DepositRequest;
import com.company.model.request.RefundRequest;
import com.company.model.request.WithdrawRequest;
import com.company.model.response.AmountTransferResponse;
import com.company.model.response.DepositResponse;
import com.company.model.response.RefundResponse;
import com.company.model.response.WithdrawResponse;
import com.flagsmith.exceptions.FlagsmithClientError;
import jakarta.transaction.Transactional;

public interface BankingService {
    @Transactional
    AmountTransferResponse transfer(AmountTransferRequest request) throws InsufficientBalanceException, AccountNotFoundException, FlagsmithClientError;

    @Transactional
    DepositResponse deposit(DepositRequest request) throws AccountNotFoundException, FlagsmithClientError;

    @Transactional
    WithdrawResponse withdraw(WithdrawRequest request) throws AccountNotFoundException, InsufficientBalanceException;

    @Transactional
    RefundResponse refund(RefundRequest request) throws AccountNotFoundException, InsufficientBalanceException;
}
