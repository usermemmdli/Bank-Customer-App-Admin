package com.example.bc_spring_app.mapper;

import com.example.bc_spring_app.dao.entity.Card;
import com.example.bc_spring_app.dto.request.CardRequest;
import com.example.bc_spring_app.dto.response.CardResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
@Mapper(componentModel = "spring")
public class CardMapper {
    public CardResponse cardToCardResponse(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .name(card.getName())
                .cardNumber(card.getCardNumber())
                .pin(card.getPin())
                .ccyCode(card.getCcyCode())
                .holderName(card.getHolderName())
                .balance(card.getBalance())
                .isActive(card.getIsActive())
                .expireDate(card.getExpireDate())
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(card.getUpdatedAt())
                .build();
    }

    public Card toEntity(CardRequest cardRequest) {
        return Card
                .builder()
                .name(cardRequest.getName())
                .cardNumber(cardRequest.getCardNumber())
                .pin(cardRequest.getPin())
                .ccyCode(cardRequest.getCcyCode())
                .holderName(cardRequest.getHolderName())
                .balance(cardRequest.getBalance())
                .isActive(true)
                .expireDate(Timestamp.valueOf(LocalDateTime.now().plusYears(4)))
                .createdAt(Timestamp.from(Instant.now()))
                .build();
    }
}
