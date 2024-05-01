package com.rewards.backend.api.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.backend.api.dtos.CustomerRewardDTO;
import com.rewards.backend.api.dtos.CustomerRewardsDTO;
import com.rewards.backend.app.customer.CustomerService;
import com.rewards.backend.app.customer.reward.CustomerReward;
import com.rewards.backend.app.customer.reward.CustomerRewardService;import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers/rewards")
public class CustomerRewardsController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRewardService customerRewardService;
    // Retrieve all customers

    // Assign reward to customer
    @PostMapping("/{customerId}/rewards/assign")
    public ResponseEntity<Object> assignRewardToCustomer(@PathVariable("customerId") String customerId, 
    		@RequestParam(required = true) String planCode) {
        // Perform the assignment logic and get the assigned reward
        CustomerReward assignedReward = customerRewardService.assignRewardToCustomer(customerId,planCode);

        return new ResponseEntity<>(assignedReward, HttpStatus.CREATED);
    }

    // Get rewards of a customer
    @GetMapping("/{customerId}/rewards")
    public ResponseEntity<List<CustomerReward>> getCustomerRewards(@PathVariable("customerId") String customerId) {
        List<CustomerReward> customerRewards = customerRewardService.getCustomerRewards(customerId);
        return new ResponseEntity<>(customerRewards, HttpStatus.OK);
    }

    // Check and update expiration status of rewards
    @PostMapping("/rewards/checkExpiration")
    public ResponseEntity<String> checkAndUpdateExpirationStatus() {
        customerRewardService.checkAndUpdateExpirationStatus();
        return ResponseEntity.ok().body("Expiration status updated successfully.");
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<CustomerRewardsDTO>> getAllCustomersWithRewards(
            @RequestParam(required = false) Boolean hasRewards,
            @RequestParam(required = false) Boolean hasExpiredRewards,
            @RequestParam(required = false) Boolean hasUnclaimedRewards,
            @RequestParam(required = false) Boolean hasClaimedRewards) {

        List<CustomerRewardsDTO> customers = customerRewardService.getAllCustomersWithRewards(hasRewards, hasExpiredRewards, hasUnclaimedRewards, hasClaimedRewards);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    
    
}