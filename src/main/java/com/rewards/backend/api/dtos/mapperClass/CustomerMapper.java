package com.rewards.backend.api.dtos.mapperClass;

import com.rewards.backend.app.customer.Customer;
import com.rewards.backend.api.dtos.CustomerRegistrationDto;
import org.modelmapper.ModelMapper;

public class CustomerMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static CustomerRegistrationDto toCustomerRegistrationDto(Customer customer) {
        return modelMapper.map(customer, CustomerRegistrationDto.class);
    }

    public static Customer toCustomer(CustomerRegistrationDto dto) {
        return modelMapper.map(dto, Customer.class);
    }
}