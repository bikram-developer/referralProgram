package com.rewards.backend.app.customer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rewards.backend.ResponseHandler;
import com.rewards.backend.api.dtos.CustomerRegistrationDto;
import com.rewards.backend.api.dtos.mapperClass.CustomerMapper;
import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.api.dtos.response.CustomerLoginResponse;
import com.rewards.backend.exception.CustomException;
import com.rewards.backend.exception.CustomerNotFoundException;
import com.rewards.backend.exception.CustomerRegistrationException;
import com.rewards.backend.exception.CustomerStatusInactiveException;
import com.rewards.backend.exception.InvalidEmailException;
import com.rewards.backend.exception.InvalidPhoneNumberException;
import com.rewards.backend.validator.CustomerValidator;

import jakarta.servlet.http.HttpServletRequest;

@Service
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
					
			String db_pass  = customerRepo.getPassword(email);
			int count = customerRepo.countNoOfActiveEmails(email);
			System.out.println("email "+ email);
			System.out.println("count "+ count);
			if(count!=1)
			{
				throw new CustomerNotFoundException("user not found");
			}
			if(customerRepo.getCustomerFromDbIfTrue(email,password) == null)
			{
				throw new CustomerNotFoundException(FAILED);
			}
			else {
				
				if(!customerRepo.CustomerStatus(email) && customerRepo.loginStatus(email)) {
					throw new CustomerStatusInactiveException("""
							access denied, you have been locked out of the account, please contact the ADMIN to resolve
								""");
				}
				
				Customer customer = customerRepo.getCustomerFromDbIfTrue(email, password);
            CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
            customerLoginResponse.setCustomerEmail(customer.getEmail());
            customerLoginResponse.setLoginStatus(customer.getActivityStatus());
            customerLoginResponse.setMessage(email + " successfull Login");
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            Date nextDay = calendar.getTime();
            String sessionId = dateFormat.format(currentDate) + " till " + dateFormat.format(nextDay);
            customerLoginResponse.setSessionId(sessionId);
            
            customerLoginResponse.setToken(null);
            return customerLoginResponse;
            }
				
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
			e.printStackTrace();
			customerLoginResponse.setLoginStatus(FAILED);
			return customerLoginResponse;
		}
	}

	@Override
	public void customerRegister(CustomerRegistrationDto entity) {
		checkEmail(entity.getEmail());
		checkPhoneNumber(entity.getMobileNumber());
		register(entity);
	}

	private Customer register(CustomerRegistrationDto entity) {
		Customer customer = CustomerMapper.toCustomer(entity);
		customer.setActive(true);
		customer.setBanned(false);
		customer.setFreezed(false);
		customer.setLocked(false);
		return customerRepo.saveAndFlush(customer);
	}

	private void checkPhoneNumber(String number) {
	    if (!CustomerValidator.isValidPhoneNumber(number)) {
	        throw new InvalidPhoneNumberException("Invalid phone number format");
	    }
	    if (numberAlreadyExists(number)) {
	        throw new CustomerRegistrationException("Phone number already exists");
	    }
	}

	private boolean numberAlreadyExists(String number) {
		 return (customerRepo.countNoOfNumberPresent(number)!=0) ? true : false;
	}

	private void checkEmail(String email) {
	    if (!CustomerValidator.isValidEmail(email)) {
	        throw new InvalidEmailException("Invalid email format");
	    }
	    if (emailAlreadyExists(email)) {
	        throw new CustomerRegistrationException("Email already exists");
	    }
	}

	private boolean emailAlreadyExists(String email) {
		return (customerRepo.countNoOfActiveEmails(email) != 0 ) ? true : false;
	}

	@Override
	public List<Customer> getAllCustomer(HttpServletRequest request) {
		try {
			
			return customerRepo.findAll();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
