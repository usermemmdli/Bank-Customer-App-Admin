package com.example.bc_spring_app.mapper;

import com.example.bc_spring_app.dao.entity.Customer;
import com.example.bc_spring_app.dto.request.CustomerRequest;
import org.junit.jupiter.api.Test;

public class CustomerMapperTest {
    @Test
    void toCustomerResponse() {
        //Arrange
        var request = new Customer();
        request.setName("Mustafa");
        request.setSurname("Mammadli");
        request.setEmail("<EMAIL>");
        request.setIsActive(true);

        //Act(actual)
        var actual = CustomerMapper.customerToCustomerResponse(request);

        //Assert
        assert actual.getName().equals("Mustafa");
        assert actual.getSurname().equals("Mammadli");
        assert actual.getEmail().equals("<EMAIL>");
        assert actual.getIsActive().equals(true);
    }

    @Test
    void toCustomerEntity() {
        //Arrange
        var request = new CustomerRequest();
        request.setName("Mustafa");
        request.setSurname("Mammadli");
        request.setEmail("<EMAIL>");
        request.setIsActive(true);

        //Act(actual)
        var actual = CustomerMapper.toEntity(request);

        //Assert
        assert actual.getName().equals("Mustafa");
        assert actual.getSurname().equals("Mammadli");
        assert actual.getEmail().equals("<EMAIL>");
        assert actual.getIsActive().equals(true);
    }
}
