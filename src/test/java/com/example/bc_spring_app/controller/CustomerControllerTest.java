package com.example.bc_spring_app.controller;

import com.example.bc_spring_app.dao.entity.Customer;
import com.example.bc_spring_app.dto.request.CustomerRequest;
import com.example.bc_spring_app.dto.response.CustomerResponse;
import com.example.bc_spring_app.exception.CustomerNotFoundException;
import com.example.bc_spring_app.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCustomersTest() throws Exception {
        List<CustomerResponse> customerResponses = new ArrayList<>();
        customerResponses.add(CustomerResponse.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .email("<EMAIL>")
                .isActive(true)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(null)
                .build());

        when(customerService.getAllCustomers()).thenReturn(customerResponses);

        ResultActions response = mockMvc.perform(get("/api/v1/customers"));
        response.andExpect(status().isOk());
        response.andReturn();
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        final Long id = 100L;

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(id)
                .name("test")
                .surname("test")
                .email("<EMAIL>")
                .isActive(true)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(null)
                .build();

        when(customerService.getCustomerById(id)).thenReturn(customerResponse);

        mockMvc.perform(get("/api/v1/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customerResponse)));
    }

    @Test
    void getByCustomerId_SHOULD_RETURN_NOT_FOUND() throws Exception {
        final Long id = 100L;

        when(customerService.getCustomerById(id)).thenReturn(null);

        mockMvc.perform(get("/api/v1/customers/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCustomer_Success() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("test");
        customerRequest.setSurname("test");
        customerRequest.setEmail("<EMAIL>");
        customerRequest.setIsActive(true);
        customerRequest.setUpdatedAt(Timestamp.from(Instant.now()));

        CustomerResponse expectedCustomerResponse = CustomerResponse.builder()
                .name(customerRequest.getName())
                .surname(customerRequest.getSurname())
                .email(customerRequest.getEmail())
                .isActive(customerRequest.getIsActive())
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();

        when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(expectedCustomerResponse);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCustomerResponse)));

    }

    @Test
    void updateCustomer_Success() throws Exception {
        Long id = 1L;

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("Updated Name");
        customerRequest.setSurname("Updated Surname");
        customerRequest.setEmail("updated@example.com");

        Customer updatedCustomer = Customer.builder()
                .id(id)
                .name(customerRequest.getName())
                .surname(customerRequest.getSurname())
                .email(customerRequest.getEmail())
                .build();

        when(customerService.updateCustomer(id, customerRequest)).thenReturn(updatedCustomer);

        mockMvc.perform(put("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedCustomer)));
    }

    @Test
    void isActiveCustomer_Success() throws Exception {
        Long id = 1L;
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setIsActive(true);  // update olunacaq field

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setIsActive(true);  // gozlenilen update olunmus deyer

        // Gozlenilen response u yaradiriq
        CustomerResponse expectedResponse = new CustomerResponse();
        expectedResponse.setId(id);
        expectedResponse.setIsActive(true);

        // Servis methodunu mock'layiriq
        when(customerService.updateIsActiveCustomer(id, customerRequest)).thenReturn(updatedCustomer);
        when(customerService.customerToCustomerResponse(updatedCustomer)).thenReturn(expectedResponse);

        mockMvc.perform(put("/api/v1/customers/is-active/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isOk())  // 200 OK gozlenilir
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void deleteCustomer_Success() throws Exception {
        var id = 1L;

        doNothing().when(customerService).deleteCustomer(id);

        mockMvc.perform(delete("/api/v1/customers/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomer_NotFound() throws Exception {
        Long id = 999L;

        doThrow(new CustomerNotFoundException("Card not found")).when(customerService).deleteCustomer(id);

        mockMvc.perform(delete("/api/v1/customers/{id}", id))
                .andExpect(status().isNotFound());
    }
}
