package com.rewards.backend.app.customer;

import java.util.List;

import com.rewards.backend.api.dtos.CustomerReferralStatus;
import com.rewards.backend.api.dtos.CustomerRegistrationDto;
import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.api.dtos.request.UserPermissionUpdateRequest;
import com.rewards.backend.api.dtos.response.CustomerLoginResponse;
import com.rewards.backend.api.dtos.response.UserPermissionAll;

import jakarta.servlet.http.HttpServletRequest;

public interface CustomerService {

	public CustomerLoginResponse customerLogin(CustomerLoginRequest inputs);

	public void customerRegister(CustomerRegistrationDto entity);

	public List<Customer> getAllCustomer(HttpServletRequest request);
	
	public void referCustomer(String referralCode, Long referrerId);

    public List<CustomerReferralStatus> getReferralStatus(Long customerId);

	public void addReferrer(Long customerId, Long referrerId);
	
	public void customerRegisterWithReferral(CustomerRegistrationDto entity);

	public String getReferralCode(String email);
	
	public Customer getById(String customerId);

	public List<UserPermissionAll> getAllUserPermission(HttpServletRequest request);

	public UserPermissionAll updateUserPermission(long userId, UserPermissionUpdateRequest request);
}
