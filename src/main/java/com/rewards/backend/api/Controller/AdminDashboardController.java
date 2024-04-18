package com.rewards.backend.api.Controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.backend.ResponseHandler;
import com.rewards.backend.api.dtos.CustomerDashboard;
import com.rewards.backend.api.dtos.mapperClass.CustomerMapper;
import com.rewards.backend.app.customer.Customer;
import com.rewards.backend.app.customer.CustomerService;
import com.rewards.backend.exception.CustomException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/auth/dashboard")
public class AdminDashboardController {

	
	private final CustomerService customerService;
	
	public AdminDashboardController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping(value = "allCustomers")
	public ResponseEntity<?> getAllCustomers(HttpServletRequest request) {
		try {
			List<Customer>response = customerService.getAllCustomer(request);
			List<CustomerDashboard> newResoponse= new LinkedList<>();
			for(Customer customer : response) {
			newResoponse.add(CustomerMapper.toDashboardList(customer));
			}
			return ResponseHandler.generateResponse(newResoponse, 
					HttpStatus.OK	, 
					"Ok");
		} catch (CustomException ce) {
			ce.printStackTrace();
			return ResponseHandler.generateResponse(ce.getMessage(), 
					HttpStatus.BAD_REQUEST,
					"Failed to load data");			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load data");
		}
	}
	
	
	
}
