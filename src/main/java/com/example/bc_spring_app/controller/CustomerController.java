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

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> allCustomers = customerService.getAllCustomers();
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        if (customerResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping("/new")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequest customerRequest) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerRequest);
        CustomerResponse customerResponse = customerService.customerToCustomerResponse(updatedCustomer);
        return ResponseEntity.ok(customerResponse);
    }

    @PatchMapping("/is-active/{id}")
    public ResponseEntity<CustomerResponse> updateIsActiveCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequest customerRequest) {
        Customer updatedCustomer = customerService.updateIsActiveCustomer(id, customerRequest);
        CustomerResponse customerResponse = customerService.customerToCustomerResponse(updatedCustomer);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
