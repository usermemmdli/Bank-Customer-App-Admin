package com.example.bc_spring_app.controller;

import com.example.bc_spring_app.dao.entity.Customer;
import com.example.bc_spring_app.dto.response.TransactionResponse;
import com.example.bc_spring_app.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    void getAllTransactionsTest() throws Exception {
        // Arrange
        List<TransactionResponse> transactionList = Arrays.asList(
                new TransactionResponse(1L, "test", "12312124", "12342432", Customer.builder().id(1L).build(), "send", 50.0, Timestamp.from(Instant.now())),
                new TransactionResponse(2L, "test", "42353454", "34524352", Customer.builder().id(2L).build(), "send", 34.0, Timestamp.from(Instant.now()))
        );

        when(transactionService.getAllTransactions()).thenReturn(transactionList);

        // Act & Assert
        ResultActions response = mockMvc.perform(get("/api/v1/transactions"));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getTransactionByIdTest() throws Exception {
        final Long id = 100L;
        final Customer customerId = Customer.builder().id(1L).build();

        TransactionResponse transactionResponse = TransactionResponse.builder()
                .id(id)
                .description("test")
                .senderCardNumber("12312124")
                .receiverCardNumber("12342432")
                .customerId(customerId)
                .status("send")
                .amount(34.0)
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        when(transactionService.getTransactionById(id)).thenReturn(transactionResponse);

        mockMvc.perform(get("/api/v1/transactions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(transactionResponse)))  // JSON karşılaştırması
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getByTransactionId_SHOULD_RETURN_NOT_FOUND() throws Exception {
        final Long id = 100L;
        when(transactionService.getTransactionById(id)).thenReturn(null);

        mockMvc.perform(get("/api/v1/transactions/{id}", id))
                .andExpect(status().isNotFound());
    }
}
