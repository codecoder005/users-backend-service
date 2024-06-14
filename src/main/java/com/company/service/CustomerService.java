package com.company.service;

import com.company.dto.CustomerDto;
import com.company.exception.CustomerExistsException;
import com.company.exception.CustomerNotFoundException;
import com.company.model.CreateNewCustomerRequest;
import com.company.model.CreateNewCustomerResponse;
import com.company.model.UpdateCustomerDetailsRequest;
import com.company.model.UpdateCustomerDetailsResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CreateNewCustomerResponse createNewCustomer(CreateNewCustomerRequest request) throws CustomerExistsException;

    UpdateCustomerDetailsResponse updateCustomerDetails(UUID uuid, UpdateCustomerDetailsRequest request) throws CustomerNotFoundException;

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerByUUID(UUID uuid) throws CustomerNotFoundException;

    void deleteCustomer(UUID uuid);
}
