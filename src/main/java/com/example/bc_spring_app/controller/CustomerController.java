package com.example.bc_spring_app.controller;

import com.example.bc_spring_app.dao.entity.Customer;
import com.example.bc_spring_app.dto.request.CustomerRequest;
import com.example.bc_spring_app.dto.response.CustomerResponse;
import com.example.bc_spring_app.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        if (customerResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return customerService.createCustomer(customerRequest);
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequest customerRequest) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerRequest);
        return customerService.customerToCustomerResponse(updatedCustomer);
    }

    @PutMapping("/is-active/{id}")
    public CustomerResponse updateIsActiveCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequest customerRequest) {
        Customer updatedCustomer = customerService.updateIsActiveCustomer(id, customerRequest);
        return customerService.customerToCustomerResponse(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer removed";
    }
}
