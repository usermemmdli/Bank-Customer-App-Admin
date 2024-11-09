package com.example.bc_spring_app.dto.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CardRequest {
    private String name;
    private String cardNumber;
    private String pin;
    private String ccyCode;
    private String holderName;
    private Double balance;
    private Boolean isActive;
    private Timestamp expireDate;
    private Timestamp updatedAt;
}
