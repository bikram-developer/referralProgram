package com.rewards.backend.app.customer.reward;

import java.util.List;

import com.rewards.backend.api.dtos.CustomerRewardDTO;
import com.rewards.backend.api.dtos.CustomerRewardsDTO;

public interface CustomerRewardService {
    CustomerReward assignRewardToCustomer(String customerId, String rewardPlanId);
    List<CustomerReward> getCustomerRewards(String customerId);
    void checkAndUpdateExpirationStatus();
	List<CustomerRewardsDTO> getAllCustomersWithRewards(Boolean hasRewards, Boolean hasExpiredRewards,
			Boolean hasUnclaimedRewards, Boolean hasClaimedRewards);
}