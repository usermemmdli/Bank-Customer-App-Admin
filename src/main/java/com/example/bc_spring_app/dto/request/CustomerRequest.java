package com.example.bc_spring_app.dto.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CustomerRequest {
    private String name;
    private String surname;
    private String email;
    private Boolean isActive;
    private Timestamp updatedAt;
}
