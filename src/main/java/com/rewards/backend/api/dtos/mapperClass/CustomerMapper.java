package com.rewards.backend.api.dtos.mapperClass;

import org.modelmapper.ModelMapper;

import com.rewards.backend.api.dtos.CustomerDashboard;
import com.rewards.backend.api.dtos.CustomerRegistrationDto;
import com.rewards.backend.app.customer.Customer;

public class CustomerMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static CustomerRegistrationDto toCustomerRegistrationDto(Customer customer) {
        return modelMapper.map(customer, CustomerRegistrationDto.class);
    }

    public static Customer toCustomer(CustomerRegistrationDto dto) {
        return modelMapper.map(dto, Customer.class);
    }
    
    public static CustomerDashboard toDashboardList(Customer Customer) {
    	return modelMapper.map(Customer, CustomerDashboard.class);
    }
}