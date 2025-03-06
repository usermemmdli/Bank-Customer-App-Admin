package com.example.bc_spring_app.mapper;
import com.example.bc_spring_app.dao.entity.Customer;
import com.example.bc_spring_app.dto.request.CustomerRequest;
import com.example.bc_spring_app.dto.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
@Mapper(componentModel = "spring")
public class CustomerMapper {
    public CustomerResponse customerToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .isActive(customer.getIsActive())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    public Customer toEntity(CustomerRequest customerRequest){
        return Customer
                .builder()
                .name(customerRequest.getName())
                .surname(customerRequest.getSurname())
                .email(customerRequest.getEmail())
                .isActive(true)
                .createdAt(Timestamp.from(Instant.now()))
                .build();
    }
}
