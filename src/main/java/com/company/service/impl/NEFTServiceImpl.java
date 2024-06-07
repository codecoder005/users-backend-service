package com.company.service.impl;

import com.company.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NEFTServiceImpl {
    private final BankAccountRepository bankAccountRepository;
}
