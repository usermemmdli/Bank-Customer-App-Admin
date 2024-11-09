package com.example.bc_spring_app.dao.repository;

import com.example.bc_spring_app.dao.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    @Query(value = "SELECT c FROM Customer c " +
//            "JOIN FETCH c.cardList cl " +
// bunu acma-> //"JOIN FETCH c.transactionList tl " +
//            "WHERE c.id = :customerId")
//    Optional<Customer> findCustomerWithCardsAndTransactionsById(@Param("customerId") Long id);

    @EntityGraph(attributePaths = {"cardList", "transactionList"})
    Optional<Customer> findById(Long customerId);
}
