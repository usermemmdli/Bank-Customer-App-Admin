package com.example.bc_spring_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
