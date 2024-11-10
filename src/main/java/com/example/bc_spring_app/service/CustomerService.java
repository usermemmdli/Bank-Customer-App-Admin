package com.example.bc_spring_app.service;

import com.example.bc_spring_app.dao.entity.Customer;
import com.example.bc_spring_app.dao.repository.CustomerRepository;
import com.example.bc_spring_app.dto.request.CustomerRequest;
import com.example.bc_spring_app.dto.response.CustomerResponse;
import com.example.bc_spring_app.exception.CustomerNotFoundException;
import com.example.bc_spring_app.exception.CustomerRequestInvalidError;
import com.example.bc_spring_app.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(CustomerMapper::customerToCustomerResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(CustomerMapper::customerToCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        if (customerRequest.getName() == null
                || customerRequest.getSurname() == null
                || customerRequest.getEmail() == null) {
            throw new CustomerRequestInvalidError("Customer name or surname or email is null");
        }
        Customer customer = CustomerMapper.toEntity(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.customerToCustomerResponse(savedCustomer);
    }

    public Customer updateCustomer(Long id, CustomerRequest customerRequest) {
        return customerRepository
                .findById(id)
                .map(customer -> {
                    if (customerRequest.getName() != null) {
                        customer.setName(customerRequest.getName());
                    }
                    if (customerRequest.getSurname() != null) {
                        customer.setSurname(customerRequest.getSurname());
                    }
                    if (customerRequest.getEmail() != null) {
                        customer.setEmail(customerRequest.getEmail());
                    }
                    customer.setUpdatedAt(Timestamp.from(Instant.now()));
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
    }

    public CustomerResponse customerToCustomerResponse(Customer customer) {
        return CustomerMapper.customerToCustomerResponse(customer);
    }

    public Customer updateIsActiveCustomer(Long id, CustomerRequest customerRequest) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setIsActive(customerRequest.getIsActive());
                    customer.setUpdatedAt(Timestamp.from(Instant.now()));
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
    }

    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }
    }
}

