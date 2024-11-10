package com.example.bc_spring_app.service;

import com.example.bc_spring_app.dao.entity.Card;
import com.example.bc_spring_app.dao.repository.CardRepository;
import com.example.bc_spring_app.dto.request.CardRequest;
import com.example.bc_spring_app.dto.response.CardResponse;
import com.example.bc_spring_app.exception.CardNotFoundException;
import com.example.bc_spring_app.exception.CardRequestInvalidError;
import com.example.bc_spring_app.mapper.CardMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public List<CardResponse> getAllCards() {
        return cardRepository
                .findAll()
                .stream()
                .map(CardMapper::cardToCardResponse)
                .collect(Collectors.toList());
    }

    public CardResponse getCardById(Long id) {
        return cardRepository
                .findById(id)
                .map(CardMapper::cardToCardResponse)
                .orElseThrow(() -> new CardNotFoundException("Card with id " + id + " not found"));
    }

    public CardResponse createCard(CardRequest cardRequest) {
        if (cardRequest.getName() == null
                || cardRequest.getCardNumber() == null
                || cardRequest.getPin() == null
                || cardRequest.getCcyCode() == null
                || cardRequest.getHolderName() == null
                || cardRequest.getBalance() == null
        ) {
            throw new CardRequestInvalidError("Card request invalid");
        }
        Card card = CardMapper.toEntity(cardRequest);
        Card savedCard = cardRepository.save(card);
        return CardMapper.cardToCardResponse(savedCard);
    }

    public Card updateCard(Long id, CardRequest cardRequest) {
        return cardRepository
                .findById(id)
                .map(card -> {
                    if (cardRequest.getName() != null) {
                        card.setName(cardRequest.getName());
                    }
                    if (cardRequest.getCardNumber() != null) {
                        card.setCardNumber(cardRequest.getCardNumber());
                    }
                    if (cardRequest.getPin() != null) {
                        card.setPin(cardRequest.getPin());
                    }
                    if (cardRequest.getCcyCode() != null) {
                        card.setCcyCode(cardRequest.getCcyCode());
                    }
                    if (cardRequest.getHolderName() != null) {
                        card.setHolderName(cardRequest.getHolderName());
                    }
                    if (cardRequest.getExpireDate() != null) {
                        card.setExpireDate(cardRequest.getExpireDate());
                    }
                    card.setUpdatedAt(Timestamp.from(Instant.now()));
                    return cardRepository.save(card);
                })
                .orElseThrow(() -> new CardNotFoundException("Card with id " + id + " not found"));
    }

    public CardResponse cardToCardResponse(Card card) {
        return CardMapper.cardToCardResponse(card);
    }

    public Card updateIsActiveCard(Long id, CardRequest cardRequest) {
        return cardRepository
                .findById(id)
                .map(card -> {
                    card.setIsActive(cardRequest.getIsActive());
                    card.setUpdatedAt(Timestamp.from(Instant.now()));
                    return cardRepository.save(card);
                })
                .orElseThrow(() -> new CardNotFoundException("Card with id " + id + " not found"));
    }

    public void deleteCard(Long id) {
        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
        } else {
            throw new CardNotFoundException("Card with id " + id + " not found");
        }
    }
}
