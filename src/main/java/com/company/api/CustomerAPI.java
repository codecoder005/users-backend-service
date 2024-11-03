package com.company.api;

import com.company.dto.CustomerDto;
import com.company.model.request.CreateNewCustomerRequest;
import com.company.model.request.UpdateCustomerDetailsRequest;
import com.company.model.response.CreateNewCustomerResponse;
import com.company.model.response.UpdateCustomerDetailsResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public interface CustomerAPI {
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<CreateNewCustomerResponse> createNewCustomer(
            @Valid @RequestBody CreateNewCustomerRequest request
    );

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<CustomerDto>> getAllCustomers();

    @GetMapping(
            value = "/{uuid}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<CustomerDto> getCustomerByUUID(@PathVariable UUID uuid);

    @PutMapping("/{uuid}")
    ResponseEntity<UpdateCustomerDetailsResponse> updateCustomerDetails(
            @PathVariable UUID uuid,
            @RequestBody UpdateCustomerDetailsRequest request
    );

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID uuid);
}
