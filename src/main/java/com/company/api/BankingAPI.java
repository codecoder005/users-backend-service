package com.company.api;

import com.company.model.request.AmountTransferRequest;
import com.company.model.request.DepositRequest;
import com.company.model.request.WithdrawRequest;
import com.company.model.response.AmountTransferResponse;
import com.company.model.response.DepositResponse;
import com.company.model.response.WithdrawResponse;
import com.flagsmith.exceptions.FlagsmithClientError;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/banking")
public interface BankingAPI {
    @PostMapping(
            value = "/transfer",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<AmountTransferResponse> transfer(
            @Valid @RequestBody AmountTransferRequest request
    ) throws FlagsmithClientError;

    @PostMapping(value = "/deposit", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<DepositResponse> deposit(@Valid @RequestBody DepositRequest request) throws FlagsmithClientError;

    @PostMapping(value = "/withdraw", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<WithdrawResponse> withdraw(@Valid @RequestBody WithdrawRequest request);
}
