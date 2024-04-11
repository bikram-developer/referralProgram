package com.rewards.backend.app.customer;

import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.api.dtos.response.CustomerLoginResponse;

public class CustomerServiceImpl implements CustomerService{

	private final CustomerRepo customerRepo;
	private static final String FAILED = "Failed to verify user"
	public CustomerServiceImpl (CustomerRepo customerRepo) {
		this.customerRepo= customerRepo;
	}

	@Override
	public CustomerLoginResponse customerLogin(CustomerLoginRequest inputs) {

		try {
			// check if email exists
			String db_pass  = customerRepo.getPassword(inputs.getEmail());
//			check in db and also check in application
			// check other params
			// check if deleted or no
			// get the password
		} catch (Exception e) {
			// TODO: handle exception
			CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
			customerLoginResponse.setLoginStatus(FAILED);
		}
	}
	
	
}
