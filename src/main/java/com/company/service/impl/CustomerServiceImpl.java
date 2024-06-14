package com.company.service.impl;

import com.company.dto.CustomerDto;
import com.company.entity.CustomerEntity;
import com.company.entity.KYCStatus;
import com.company.exception.CustomerExistsException;
import com.company.exception.CustomerNotFoundException;
import com.company.exception.PanIsInUseException;
import com.company.model.CreateNewCustomerRequest;
import com.company.model.CreateNewCustomerResponse;
import com.company.model.UpdateCustomerDetailsRequest;
import com.company.model.UpdateCustomerDetailsResponse;
import com.company.repository.BankAccountRepository;
import com.company.repository.CustomerRepository;
import com.company.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateNewCustomerResponse createNewCustomer(CreateNewCustomerRequest request) throws CustomerExistsException {
        log.info("CustomerServiceImpl::createNewCustomer");
        customerRepository.findByAadharNo(request.getAadharNo())
                .ifPresent(customer -> {
                    throw new CustomerExistsException("Customer already exists. customerId: " + customer.getUuid());
                });
        customerRepository.findByPan(request.getPan())
                .ifPresent(customer -> {
                    throw new PanIsInUseException(
                            String.format("pan %s is used by another customerId: %s", request.getPan(), customer.getUuid())
                    );
                });
        CustomerEntity draftedCustomer = modelMapper.map(request, CustomerEntity.class);
        draftedCustomer.setKycStatus(KYCStatus.INITIATED);
        CustomerEntity createdCustomer = customerRepository.save(draftedCustomer);
        return CreateNewCustomerResponse.builder()
                .uuid(createdCustomer.getUuid())
                .build();
    }

    @Override
    @Cacheable(value = "CUSTOMER", unless = "#result.size() < 1")
    public List<CustomerDto> getAllCustomers() {
        log.info("CustomerServiceImpl::getAllCustomers");
        return customerRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CustomerDto.class))
                .toList();
    }

    @Override
    @Cacheable(value = "CUSTOMER", key = "#uuid", unless = "#result == null")
    public CustomerDto getCustomerByUUID(UUID uuid) throws CustomerNotFoundException {
        log.info("CustomerServiceImpl::getCustomerByUUID");
        AtomicReference<CustomerDto> atomicCustomerReference = new AtomicReference<>();
        customerRepository.findById(uuid)
                .ifPresentOrElse(
                        c -> atomicCustomerReference.set(modelMapper.map(c, CustomerDto.class)),
                        () -> {
                            throw new CustomerNotFoundException("No Customer found with id " + uuid);
                        }
                );
        return atomicCustomerReference.get();
    }

    @Override
    public UpdateCustomerDetailsResponse updateCustomerDetails(UUID uuid, UpdateCustomerDetailsRequest request) throws CustomerNotFoundException {
        log.info("CustomerServiceImpl::update");
        return null;
    }

    @Override
    @CacheEvict(value = "CUSTOMER", key = "#uuid")
    @Transactional
    public void deleteCustomer(UUID uuid) {
        log.info("CustomerServiceImpl::deleteCustomer");
        bankAccountRepository.deleteBankAccountByCustomerUUID(uuid);
        customerRepository.deleteById(uuid);
    }
}
