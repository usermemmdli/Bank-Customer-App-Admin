package com.example.bc_spring_app.controller;

import com.example.bc_spring_app.dao.entity.Card;
import com.example.bc_spring_app.dao.entity.Customer;
import com.example.bc_spring_app.dto.request.CardRequest;
import com.example.bc_spring_app.dto.response.CardResponse;
import com.example.bc_spring_app.exception.CardNotFoundException;
import com.example.bc_spring_app.service.CardService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest {
    @MockBean
    private CardService cardService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCardsTest() throws Exception {
        final Customer customerId = Customer.builder().id(1L).build();
        List<CardResponse> cardResponses = new ArrayList<>();
        cardResponses.add(CardResponse.builder()
                .id(1L)
                .name("test")
                .cardNumber("123456789")
                .pin("1234")
                .holderName("test")
                .balance(100.0)
                .customerId(customerId)
                .isActive(true)
                .expireDate(Timestamp.from(Instant.now()))
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(null)
                .build());

        when(cardService.getAllCards()).thenReturn(cardResponses);

        ResultActions response = mockMvc.perform(get("/api/v1/cards"));
        response.andExpect(status().isOk());
        response.andReturn();
    }

    @Test
    void getCardByIdTest() throws Exception {
        final Long id = 100L;
        final Customer customerId = Customer.builder().id(1L).build();

        CardResponse cardResponse = CardResponse.builder()
                .id(id)
                .name("test")
                .cardNumber("123456789")
                .pin("1234")
                .holderName("test")
                .balance(100.0)
                .customerId(customerId)
                .isActive(true)
                .expireDate(Timestamp.from(Instant.now()))
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(null)
                .build();

        when(cardService.getCardById(id)).thenReturn(cardResponse);

        mockMvc.perform(get("/api/v1/cards/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cardResponse)));
    }

    @Test
    void getByCardId_SHOULD_RETURN_NOT_FOUND() throws Exception {
        final Long id = 100L;

        when(cardService.getCardById(id)).thenReturn(null);

        mockMvc.perform(get("/api/v1/cards/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCard_Success() throws Exception {
        CardRequest cardRequest = new CardRequest();
        cardRequest.setName("test");
        cardRequest.setCardNumber("123456789");
        cardRequest.setPin("1234");
        cardRequest.setHolderName("test");
        cardRequest.setBalance(100.0);
        cardRequest.setUpdatedAt(Timestamp.from(Instant.now()));
        cardRequest.setExpireDate(Timestamp.from(Instant.now()));
        cardRequest.setIsActive(true);
        cardRequest.setCcyCode("345");

        CardResponse expectedCardResponse = new CardResponse();
        expectedCardResponse.setName(cardRequest.getName());
        expectedCardResponse.setCardNumber(cardRequest.getCardNumber());
        expectedCardResponse.setPin(cardRequest.getPin());
        expectedCardResponse.setHolderName(cardRequest.getHolderName());
        expectedCardResponse.setBalance(cardRequest.getBalance());
        expectedCardResponse.setCreatedAt(Timestamp.from(Instant.now()));
        expectedCardResponse.setUpdatedAt(Timestamp.from(Instant.now()));
        expectedCardResponse.setExpireDate(Timestamp.from(Instant.now()));
        expectedCardResponse.setIsActive(cardRequest.getIsActive());
        expectedCardResponse.setCcyCode(cardRequest.getCcyCode());

        when(cardService.createCard(any(CardRequest.class))).thenReturn(expectedCardResponse);

        mockMvc.perform(post("/api/v1/cards")
                        .contentType(MediaType.APPLICATION_JSON)  // Set the content type here
                        .content(objectMapper.writeValueAsString(cardRequest)))  // Add request body content here
                .andExpect(status().isCreated())  // Expecting 201 Created status
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCardResponse)));  // Expecting response content to match request content
    }


    @Test
    void updateCard_Success() throws Exception {
        Long id = 1L;

        CardRequest cardRequest = new CardRequest();
        cardRequest.setName("Updated Name");
        cardRequest.setCardNumber("987654321");
        cardRequest.setPin("4321");
        cardRequest.setCcyCode("678");
        cardRequest.setHolderName("Updated Holder");

        Card updatedCard = new Card();
        updatedCard.setId(id);
        updatedCard.setName(cardRequest.getName());
        updatedCard.setCardNumber(cardRequest.getCardNumber());
        updatedCard.setPin(cardRequest.getPin());
        updatedCard.setCcyCode(cardRequest.getCcyCode());
        updatedCard.setHolderName(cardRequest.getHolderName());

        when(cardService.updateCard(id, cardRequest)).thenReturn(updatedCard);

        mockMvc.perform(put("/api/v1/cards/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardRequest)))
                .andExpect(status().isOk())  // 200 OK
                .andExpect(content().json(objectMapper.writeValueAsString(updatedCard)));
    }

    @Test
    void isActiveCard_Success() throws Exception {
        var id = 1L;

        CardRequest cardRequest = new CardRequest();
        cardRequest.setIsActive(true);

        Card updatedCard = new Card();
        updatedCard.setIsActive(cardRequest.getIsActive());

        when(cardService.updateIsActiveCard(id, cardRequest)).thenReturn(updatedCard);

        mockMvc.perform(put("/api/v1/cards/is-active/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedCard)));
    }

    @Test
    void deleteCard_Success() throws Exception {
        var id = 1L;

        doNothing().when(cardService).deleteCard(id);

        mockMvc.perform(delete("/api/v1/cards/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCard_NotFound() throws Exception {
        Long id = 999L;

        doThrow(new CardNotFoundException("Card not found")).when(cardService).deleteCard(id);

        mockMvc.perform(delete("/api/v1/cards/{id}", id))
                .andExpect(status().isNotFound());
    }
}