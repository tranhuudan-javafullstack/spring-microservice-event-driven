package com.huudan.customer.service.impl;

import com.huudan.customer.command.event.CustomerUpdatedEvent;
import com.huudan.customer.constants.CustomerConstants;
import com.huudan.customer.dto.CustomerDto;
import com.huudan.customer.entity.Customer;
import com.huudan.customer.exception.CustomerAlreadyExistsException;
import com.huudan.customer.exception.ResourceNotFoundException;
import com.huudan.customer.mapper.CustomerMapper;
import com.huudan.customer.repository.CustomerRepository;
import com.huudan.customer.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void createCustomer(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndActiveSw(
                customer.getMobileNumber(), true);
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                                                     + customer.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        return CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    }

    @Override
    public boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(customerUpdatedEvent.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", customerUpdatedEvent.getMobileNumber()));
        CustomerMapper.mapEventToCustomer(customerUpdatedEvent, customer);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customerId)
        );
        customer.setActiveSw(CustomerConstants.IN_ACTIVE_SW);
        customerRepository.save(customer);
        return true;
    }

}
