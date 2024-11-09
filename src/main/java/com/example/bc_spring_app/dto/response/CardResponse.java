package com.example.bc_spring_app.dto.response;

import com.example.bc_spring_app.dao.entity.Customer;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {
    private Long id;
    private String name;
    private String cardNumber;
    private String pin;
    private String ccyCode;
    private String holderName;
    private Double balance;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customerId;
    private Boolean isActive;
    private Timestamp expireDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
