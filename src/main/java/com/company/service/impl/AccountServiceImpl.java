package com.company.service.impl;

import com.company.dto.BankAccountDto;
import com.company.entity.AccountStatus;
import com.company.entity.BankAccountEntity;
import com.company.entity.CustomerEntity;
import com.company.exception.AccountExistsException;
import com.company.exception.CustomerNotFoundException;
import com.company.model.request.CreateNewBankAccountRequest;
import com.company.model.response.CreateNewBankAccountResponse;
import com.company.repository.BankAccountRepository;
import com.company.repository.CustomerRepository;
import com.company.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateNewBankAccountResponse createNewAccount(CreateNewBankAccountRequest request) throws AccountExistsException {
        log.info("AccountServiceImpl::createNewAccount");
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("No Customer found with id: " + request.getCustomerId()));
        bankAccountRepository.findByCustomerUUID(request.getCustomerId())
                .ifPresent(account -> {
                    throw new AccountExistsException("Customer already has an account. accountId: " + account.getUuid());
                });
        BankAccountEntity newAccount = modelMapper.map(request, BankAccountEntity.class);
        newAccount.setCustomer(customer);
        newAccount.setAccountStatus(AccountStatus.CREATED);
        newAccount.setBalanceOnHold(BigDecimal.ZERO);
        newAccount.setBalanceOnBlocked(BigDecimal.ZERO);
        newAccount = bankAccountRepository.save(newAccount);
        return CreateNewBankAccountResponse.builder()
                .accountId(newAccount.getUuid())
                .customerId(customer.getUuid())
                .message("Account created successfully")
                .build();
    }

    @Override
    public BankAccountDto getAccountByUUID(UUID uuid) {
        BankAccountEntity bankAccount = bankAccountRepository.findById(uuid)
                .orElseThrow(() -> new CustomerNotFoundException("No Customer found with id: " + uuid));
        return modelMapper.map(bankAccount, BankAccountDto.class);
    }

    @Override
    public List<BankAccountDto> getAllAccounts() {
        log.info("AccountServiceImpl::getAllAccounts");
        return bankAccountRepository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, BankAccountDto.class))
                .toList();
    }
}
