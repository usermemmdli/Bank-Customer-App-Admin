package com.example.bc_spring_app.mapper;

import com.example.bc_spring_app.dao.entity.Transaction;
import com.example.bc_spring_app.dto.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class TransactionMapper {
    public TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .senderCardNumber(transaction.getSenderCardNumber())
                .receiverCardNumber(transaction.getReceiverCardNumber())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
