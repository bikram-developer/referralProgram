package com.rewards.backend.api.dtos.mapperClass;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rewards.backend.api.dtos.CustomerDashboard;
import com.rewards.backend.api.dtos.CustomerRegistrationDto;
import com.rewards.backend.api.dtos.EachCustomerLeadData;
import com.rewards.backend.app.customer.Customer;
import com.rewards.backend.app.customer.CustomerRepo;

@Component
public class CustomerMapper {

	@Autowired
	private CustomerRepo customerRepo;
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
    
    public EachCustomerLeadData toEachCustomerLeadData(Customer customer) {
        EachCustomerLeadData leadData = modelMapper.map(customer, EachCustomerLeadData.class);

        Optional<List<Customer>> referralListOptional = customerRepo.getreferralInfoByCustomerId(String.valueOf(customer.getId()));
        List<CustomerDashboard> referralList = referralListOptional.orElse(Collections.emptyList())
                .stream()
                .map(this::toCustomerDashboard)
                .collect(Collectors.toList());

        leadData.setReferralList(referralList);

        return leadData;
    }

    public CustomerDashboard toCustomerDashboard(Customer customer) {
        return modelMapper.map(customer, CustomerDashboard.class);
    }
}