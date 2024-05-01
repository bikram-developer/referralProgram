package com.rewards.backend.app.customer.reward;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rewards.backend.app.customer.Customer;

@Repository
public interface CustomerRewardRepository extends JpaRepository<CustomerReward, Long> {
    // Add custom query methods if needed
	
    List<CustomerReward> findByExpirationDateBeforeAndExpiredFalse(LocalDate currentDate);

    
    List<CustomerReward> findByCustomer(Customer customer);

}
