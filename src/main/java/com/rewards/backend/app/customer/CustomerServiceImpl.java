package com.rewards.backend.app.customer;

import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.api.dtos.response.CustomerLoginResponse;
import com.rewards.backend.exception.CustomerNotFoundException;
import com.rewards.backend.exception.CustomerStatusInactiveException;

public class CustomerServiceImpl implements CustomerService{

	private final CustomerRepo customerRepo;
	private static final String FAILED = "Failed to verify user";
	public CustomerServiceImpl (CustomerRepo customerRepo) {
		this.customerRepo= customerRepo;
	}

	@Override
	public CustomerLoginResponse customerLogin(CustomerLoginRequest inputs) {

		try {
			String email = inputs.getEmail();
			String password = inputs.getPassword();
					
			// check if email exists
			String db_pass  = customerRepo.getPassword(email);
//			check in db and also check in application
			// check other params
			// check if deleted or no
			// get the password
			int count = customerRepo.countNoOfActiveEmails(email);
			if(count!=1)
			{
				throw new CustomerNotFoundException("user not found");
			}
			if(customerRepo.getCustomerFromDbIfTrue(email,password) == null) {}
			{
				if(!customerRepo.CustomerStatus(email) && !customerRepo.loginStatus(email)) {
					throw new CustomerStatusInactiveException("""
							access denied, you have been locked out of the account, please contact the ADMIN to resolve
								""");
				}
			}return null;
		}	catch (CustomerStatusInactiveException csi) {
			CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
			customerLoginResponse.setLoginStatus(csi.getMessage());
			return customerLoginResponse;
		}
		catch (CustomerNotFoundException cnfe) {
			CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
			customerLoginResponse.setLoginStatus(cnfe.getMessage());
			return customerLoginResponse;
		} 
		catch (Exception e) {
			CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
			customerLoginResponse.setLoginStatus(FAILED);
			return customerLoginResponse;
		}
	}
}
