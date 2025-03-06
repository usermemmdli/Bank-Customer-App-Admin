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

    @GetMapping("/all")
    public ResponseEntity<List<CardResponse>> getAllCards() {
        List<CardResponse> cardResponses = cardService.getAllCards();
        return ResponseEntity.ok(cardResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable Long id) {
        CardResponse card = cardService.getCardById(id);
        if (card == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(card);
    }

    @PostMapping("/new")
    public ResponseEntity<CardResponse> createCard(@RequestBody @Valid CardRequest cardRequest) {
        CardResponse cardResponse = cardService.createCard(cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> updateCard(@PathVariable Long id, @RequestBody @Valid CardRequest cardRequest) {
        Card updatedCard = cardService.updateCard(id, cardRequest);
        CardResponse cardResponse = cardService.cardToCardResponse(updatedCard);
        return ResponseEntity.ok(cardResponse);
    }

    @PatchMapping("/is-active/{id}")
    public ResponseEntity<CardResponse> updateIsActiveCardsActiveCard(@PathVariable Long id, @RequestBody @Valid CardRequest cardRequest) {
        Card updatedCard = cardService.updateCard(id, cardRequest);
        CardResponse cardResponse = cardService.cardToCardResponse(updatedCard);
        return ResponseEntity.ok(cardResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }
}
