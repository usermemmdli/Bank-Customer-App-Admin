package com.example.bc_spring_app.controller;

import com.example.bc_spring_app.dao.entity.Card;
import com.example.bc_spring_app.dto.request.CardRequest;
import com.example.bc_spring_app.dto.response.CardResponse;
import com.example.bc_spring_app.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping
    public List<CardResponse> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable Long id) {
        CardResponse card = cardService.getCardById(id);
        if (card == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(card);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponse createCard(@RequestBody @Valid CardRequest cardRequest) {
        return cardService.createCard(cardRequest);
    }

    @PutMapping("/{id}")
    public Card updateCard(@PathVariable Long id, @RequestBody @Valid CardRequest cardRequest) {
        return cardService.updateCard(id, cardRequest);
    }

    @PutMapping("/is-active/{id}")
    public Card updateIsActiveCardsActiveCard(@PathVariable Long id, @RequestBody @Valid CardRequest cardRequest) {
        return cardService.updateIsActiveCard(id, cardRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return "Card removed";
    }
}
