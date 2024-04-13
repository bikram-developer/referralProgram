package com.rewards.backend.app.customer;

import java.util.List;

import com.rewards.backend.api.dtos.CustomerRegistrationDto;
import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.api.dtos.response.CustomerLoginResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface CustomerService {

	public CustomerLoginResponse customerLogin(CustomerLoginRequest inputs);

	public void customerRegister(CustomerRegistrationDto entity);

	public List<Customer> getAllCustomer(HttpServletRequest request);
	
}
