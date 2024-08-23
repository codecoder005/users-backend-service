package com.company.service;

import com.company.dto.BankAccountDto;
import com.company.exception.AccountExistsException;
import com.company.exception.AccountNotFoundException;
import com.company.model.request.CreateNewBankAccountRequest;
import com.company.model.response.CreateNewBankAccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    CreateNewBankAccountResponse createNewAccount(CreateNewBankAccountRequest request) throws AccountExistsException;

    List<BankAccountDto> getAllAccounts();

    BankAccountDto getAccountByUUID(UUID uuid) throws AccountNotFoundException;
}
