package com.rewards.backend.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.backend.ResponseHandler;
import com.rewards.backend.api.dtos.request.UserPermissionUpdateRequest;
import com.rewards.backend.app.customer.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/user-permission")
public class UserPermissionController {
	
	
	@Autowired private CustomerService customerService;
	
	@GetMapping(value = "get/all")
	public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
		try {

			return ResponseHandler.generateResponse(customerService.getAllUserPermission(request), 
					HttpStatus.OK	, "Api fetched successfully");
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed to load data");
		}
	}
	
	@PostMapping("update")
    public ResponseEntity<?> updateUserPermission(@RequestParam String id, @RequestBody UserPermissionUpdateRequest request) {
        try {
        	long userId = Long.parseLong(id);
        	return ResponseHandler.generateResponse(customerService.updateUserPermission(userId, request),
        			HttpStatus.OK, 
        			"User permission updated successfully");
        } catch (NumberFormatException e) {
        	return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST
        			, "Invalid user ID format for " + id);
        } catch (Exception e) {
        	return ResponseHandler.generateResponse(e.getMessage(),
        			HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update user permission");
        }
    }
	
}