package com.rewards.backend.api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.backend.api.dtos.CustomerReferralStatus;
import com.rewards.backend.app.customer.CustomerService;
import com.rewards.backend.exception.CustomerNotFoundException;

@RestController
@RequestMapping("api/referral")
@CrossOrigin("*")
public class ReferralController {

	@Autowired private CustomerService customerService;
	
	@GetMapping("/customers/{customerId}/referral-status")
    public ResponseEntity<?> getReferralStatus(@PathVariable Long customerId) {
        try {
            List<CustomerReferralStatus> referralStatus = customerService.getReferralStatus(customerId);
            return ResponseEntity.ok(referralStatus);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
	
	@PostMapping("/customers/{customerId}/referrer/{referrerId}")
    public ResponseEntity<?> addReferrer(@PathVariable Long customerId, @PathVariable Long referrerId) {
        try {
            customerService.addReferrer(customerId, referrerId);
            return ResponseEntity.ok("Referrer added successfully");
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
