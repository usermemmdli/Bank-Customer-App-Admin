package com.example.bc_spring_app.mapper;

import com.example.bc_spring_app.dao.entity.Card;
import com.example.bc_spring_app.dto.request.CardRequest;
import org.junit.jupiter.api.Test;

public class CardMapperTest {
    @Test
    void toCardResponse() {
        //Arrange
        var request = new Card();
        request.setName("birbank");
        request.setCardNumber("1234567890");
        request.setPin("1234");
        request.setCcyCode("344");
        request.setHolderName("Mustafa");

        //Act(actual)
        var actual = CardMapper.cardToCardResponse(request);

        //Assert
        assert actual.getName().equals("birbank");
        assert actual.getCardNumber().equals("1234567890");
        assert actual.getPin().equals("1234");
        assert actual.getCcyCode().equals("344");
        assert actual.getHolderName().equals("Mustafa");
    }

    @Test
    void toCardEntity() {
        //Arrange
        var request = new CardRequest();
        request.setName("birbank");
        request.setCardNumber("1234567890");
        request.setPin("1234");
        request.setCcyCode("344");
        request.setHolderName("Mustafa");

        //Act(actual)
        var actual = CardMapper.toEntity(request);

        //Assert
        assert actual.getName().equals("birbank");
        assert actual.getCardNumber().equals("1234567890");
        assert actual.getPin().equals("1234");
        assert actual.getCcyCode().equals("344");
        assert actual.getHolderName().equals("Mustafa");
    }
}
