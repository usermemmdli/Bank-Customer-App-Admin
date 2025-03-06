package com.example.bc_spring_app.service;

import com.example.bc_spring_app.dao.repository.TransactionRepository;
import com.example.bc_spring_app.dto.response.TransactionResponse;
import com.example.bc_spring_app.exception.TransactionNotFoundException;
import com.example.bc_spring_app.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toTransactionResponse)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id" + id + "not found"));
    }
}
