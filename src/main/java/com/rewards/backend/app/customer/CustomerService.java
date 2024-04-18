package com.rewards.backend.app.customer;

import java.util.List;

import com.rewards.backend.api.dtos.CustomerReferralStatus;
import com.rewards.backend.api.dtos.CustomerRegistrationDto;
import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.api.dtos.response.CustomerLoginResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface CustomerService {

	public CustomerLoginResponse customerLogin(CustomerLoginRequest inputs);

	public void customerRegister(CustomerRegistrationDto entity);

	public List<Customer> getAllCustomer(HttpServletRequest request);
	
    void referCustomer(String referralCode, Long referrerId);

    List<CustomerReferralStatus> getReferralStatus(Long customerId);

	public void addReferrer(Long customerId, Long referrerId);
	
    void customerRegisterWithReferral(CustomerRegistrationDto entity);

	public String getReferralCode(String email);
	
	public Customer getById(String customerId);
}
