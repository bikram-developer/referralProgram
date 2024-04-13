package com.rewards.backend.api.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.backend.ResponseHandler;
import com.rewards.backend.api.dtos.CustomerRegistrationDto;
import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.api.dtos.response.CustomerLoginResponse;
import com.rewards.backend.app.customer.CustomerService;
import com.rewards.backend.exception.CustomerRegistrationException;
import com.rewards.backend.exception.InvalidEmailException;
import com.rewards.backend.exception.InvalidPhoneNumberException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/cus/credentials/")
@CrossOrigin("*")
public class CustomerApiLoginRegisterController {

private final CustomerService customerService;
	
	public CustomerApiLoginRegisterController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping(value = "login")
	public ResponseEntity<?> userLogin(@RequestBody CustomerLoginRequest inputs, HttpServletRequest request) {
		try {
			CustomerLoginResponse customerLoginResponse = customerService.customerLogin(inputs);
			System.out.println("In Controller ");
			System.out.println(inputs.toString());
			System.out.println("Out Controller ");
			return ResponseHandler.generateResponse(customerLoginResponse, 
					HttpStatus.OK, 
					"Api call successfull");
			
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR, 
					"Failed to Login");
		}
	}
	
	@PostMapping(value = "old/register")
    public ResponseEntity<Object> userRegisterOld(@RequestBody CustomerRegistrationDto entity) {
        try {
            customerService.customerRegister(entity);
            return ResponseHandler.generateResponse(entity, HttpStatus.OK, "Customer Created");
        } catch (InvalidPhoneNumberException | InvalidEmailException | CustomerRegistrationException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "failed to create customer");
        } catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "Error");
		}
    }
	
	 @PostMapping("/register/{code}")
	    public ResponseEntity<Object> userRegister(@RequestBody CustomerRegistrationDto entity,
	    		@PathVariable String referralCode) {
	        try {
	            customerService.customerRegisterWithReferral(entity,referralCode);
	            return ResponseHandler.generateResponse(entity, HttpStatus.OK, "Customer Created");
	        } catch (InvalidPhoneNumberException | InvalidEmailException | CustomerRegistrationException e) {
	            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed to create customer");
	        } catch (Exception e) {
	            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "Error");
	        }
	    }
}
