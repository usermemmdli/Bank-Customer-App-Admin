package com.example.bc_spring_app.dao.repository;

import com.example.bc_spring_app.dao.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
