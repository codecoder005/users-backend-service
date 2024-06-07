package com.company.controller.rest;

import com.company.dto.CustomerDto;
import com.company.model.CreateNewCustomerRequest;
import com.company.model.CreateNewCustomerResponse;
import com.company.model.UpdateCustomerDetailsRequest;
import com.company.model.UpdateCustomerDetailsResponse;
import com.company.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateNewCustomerResponse> createNewCustomer(
            @Valid @RequestBody CreateNewCustomerRequest request
    ) {
        log.info("CustomerController::createNewCustomer");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createNewCustomer(request));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        log.info("CustomerController::getAllCustomers");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getAllCustomers());
    }

    @GetMapping(
            value = "/{uuid}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CustomerDto> getCustomerByUUID(@PathVariable UUID uuid) {
        log.info("CustomerController::getCustomerByUUID");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getCustomerByUUID(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UpdateCustomerDetailsResponse> updateCustomerDetails(
            @PathVariable UUID uuid,
            @RequestBody UpdateCustomerDetailsRequest request
    ) {
        log.info("CustomerController::updateCustomerDetails");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.updateCustomerDetails(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID uuid) {
        log.info("CustomerController::deleteCustomer");
        customerService.deleteCustomer(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
