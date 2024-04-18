package com.rewards.backend.api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.backend.ResponseHandler;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin")
public class AdminController{

	@GetMapping("get/users")
	public ResponseEntity<?> getMethodName(HttpServletRequest request) {
		
		return ResponseHandler.generateResponse(request, null, null);
	}

}
