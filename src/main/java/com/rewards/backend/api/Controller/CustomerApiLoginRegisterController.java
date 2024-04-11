package com.rewards.backend.api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.app.customer.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/cus/credentials")
@CrossOrigin("*")
public class CustomerApiLoginRegisterController {

private final CustomerService customerService;
	
	public CustomerApiLoginRegisterController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping(value = "login")
	public ResponseEntity<?> userLogin(CustomerLoginRequest inputs, HttpServletRequest request) {
		try {
			customerService.customerLogin(inputs);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
