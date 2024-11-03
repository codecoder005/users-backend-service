package com.company.controller.rest;

import com.company.api.CustomerAPI;
import com.company.api.PingAPI;
import com.company.dto.CustomerDto;
import com.company.model.request.CreateNewCustomerRequest;
import com.company.model.request.UpdateCustomerDetailsRequest;
import com.company.model.response.CreateNewCustomerResponse;
import com.company.model.response.PingAPIResponse;
import com.company.model.response.UpdateCustomerDetailsResponse;
import com.company.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerController implements PingAPI, CustomerAPI {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @Override
    public PingAPIResponse ping() {
        log.info("CustomerController::ping");
        return PingAPIResponse.builder()
                .status(200)
                .message("Up and Healthy")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ResponseEntity<CreateNewCustomerResponse> createNewCustomer(
            @Valid @RequestBody CreateNewCustomerRequest request
    ) {
        log.info("CustomerController::createNewCustomer");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createNewCustomer(request));
    }

    @Override
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        log.info("CustomerController::getAllCustomers");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getAllCustomers());
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerByUUID(@PathVariable UUID uuid) {
        log.info("CustomerController::getCustomerByUUID");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getCustomerByUUID(uuid));
    }

    @Override
    public ResponseEntity<UpdateCustomerDetailsResponse> updateCustomerDetails(
            @PathVariable UUID uuid,
            @RequestBody UpdateCustomerDetailsRequest request
    ) {
        log.info("CustomerController::updateCustomerDetails");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.updateCustomerDetails(uuid, request));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID uuid) {
        log.info("CustomerController::deleteCustomer");
        customerService.deleteCustomer(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
