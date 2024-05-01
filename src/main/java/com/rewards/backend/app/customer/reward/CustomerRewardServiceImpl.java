package com.rewards.backend.app.customer.reward;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewards.backend.api.dtos.CustomerRewardDTO;
import com.rewards.backend.api.dtos.CustomerRewardsDTO;
import com.rewards.backend.app.customer.Customer;
import com.rewards.backend.app.customer.CustomerRepo;
import com.rewards.backend.app.referral.referral.RewardPlan;
import com.rewards.backend.app.referral.referral.RewardPlanRepository;
import com.rewards.backend.exception.CustomException;

@Service
public class CustomerRewardServiceImpl implements CustomerRewardService {

    @Autowired
    private CustomerRewardRepository customerRewardRepository;

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private RewardPlanRepository rewardPlanRepository;

    public CustomerReward assignRewardToCustomer(String customerId, String rewardPlanId) {
        // Find customer by customerId
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomException("Customer not found with customerId: " + customerId));

        // Find reward plan by planCode
        RewardPlan rewardPlan = rewardPlanRepository.findByRewardCode(rewardPlanId)
                .orElseThrow(() -> new CustomException("Reward Plan not found with rewardCode: " + rewardPlanId));

        // Calculate expiration date based on reward plan expiration
        LocalDate assignmentDate = LocalDate.now();
        LocalDate expirationDate = null;

        if (rewardPlan.isHasExpiration()) {
            // If the reward plan has an expiration, calculate the expiration date
            expirationDate = assignmentDate.plusDays(rewardPlan.getExpirationDate());
        }

        // Create CustomerReward entity
        CustomerReward customerReward = new CustomerReward();
        customerReward.setCustomer(customer);
        customerReward.setRewardPlan(rewardPlan);
        customerReward.setAssignmentDate(assignmentDate);
        customerReward.setExpirationDate(expirationDate);
        customerReward.setExpired(false);
        customerReward.setClaimed(false);

        // Save and return the assigned reward
        return customerRewardRepository.save(customerReward);
    }


    @Override
    public List<CustomerReward> getCustomerRewards(String customerId) {
        // Find customer by customerId
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomException("Customer not found with id: " + customerId));

        // Return customer rewards
        return customer.getCustomerRewards();
    }

    @Override
    public void checkAndUpdateExpirationStatus() {
        // Get current date
        LocalDate currentDate = LocalDate.now();

        // Find rewards with expiration date <= current date
        List<CustomerReward> expiredRewards = customerRewardRepository.findByExpirationDateBeforeAndExpiredFalse(currentDate);

        // Update expiration status and save
        for (CustomerReward reward : expiredRewards) {
            reward.setExpired(true);
            customerRewardRepository.save(reward);
        }
    }

    // Helper method to calculate expiration date
    private LocalDate calculateExpirationDate(int expirationDays) {
        if (expirationDays == 0) {
            // No expiration date
            return null;
        } else {
            // Calculate expiration date based on current date
            return LocalDate.now().plusDays(expirationDays);
        }
    }
    
    @Override
    public List<CustomerRewardsDTO> getAllCustomersWithRewards(Boolean hasRewards, Boolean hasExpiredRewards, Boolean hasUnclaimedRewards, Boolean hasClaimedRewards) {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerRewardsDTO> customerDTOs = new ArrayList<>();

        for (Customer customer : customers) {
            List<CustomerReward> rewards = customerRewardRepository.findByCustomer(customer);

            // Filter rewards based on the provided conditions
            if ((hasRewards == null || !hasRewards || !rewards.isEmpty()) &&
                    (hasExpiredRewards == null || !hasExpiredRewards || hasExpiredRewards && hasExpiredReward(rewards)) &&
                    (hasUnclaimedRewards == null || !hasUnclaimedRewards || hasUnclaimedRewards && hasUnclaimedReward(rewards)) &&
                    (hasClaimedRewards == null || !hasClaimedRewards || hasClaimedRewards && hasClaimedReward(rewards))) {

            	CustomerRewardsDTO customerDTO = new CustomerRewardsDTO();
                customerDTOs.add(customerDTO);
            }
        }
        return customerDTOs;
    }

    // Helper methods to check reward conditions
    private boolean hasExpiredReward(List<CustomerReward> rewards) {
        for (CustomerReward reward : rewards) {
            if (reward.isExpired()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasUnclaimedReward(List<CustomerReward> rewards) {
        for (CustomerReward reward : rewards) {
            if (!reward.isClaimed()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasClaimedReward(List<CustomerReward> rewards) {
        for (CustomerReward reward : rewards) {
            if (reward.isClaimed()) {
                return true;
            }
        }
        return false;
    }
}
