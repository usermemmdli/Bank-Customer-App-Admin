package com.example.bc_spring_app.dao.repository;

import com.example.bc_spring_app.dao.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
