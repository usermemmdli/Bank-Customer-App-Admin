package com.example.bc_spring_app.mapper;

import com.example.bc_spring_app.dao.entity.Transaction;
import org.junit.jupiter.api.Test;

class TransactionMapperTest {
    @Test
    void toTransactionResponse() {
        //Arrange
        var request = new Transaction();
        request.setDescription("okey");
        request.setSenderCardNumber("1234567890");
        request.setReceiverCardNumber("1234567890");
        request.setStatus("ok");

        //Act(actual)
        var actual = TransactionMapper.toTransactionResponse(request);

        //Assert
        assert actual.getDescription().equals("okey");
        assert actual.getSenderCardNumber().equals("1234567890");
        assert actual.getReceiverCardNumber().equals("1234567890");
        assert actual.getStatus().equals("ok");
    }
}
