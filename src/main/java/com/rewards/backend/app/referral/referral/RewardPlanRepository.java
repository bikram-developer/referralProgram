package com.rewards.backend.app.referral.referral;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RewardPlanRepository extends JpaRepository<RewardPlan, Long>{

	
	@Query(value= "select * from reward_plan where reward_code = :reward_plan_code", nativeQuery= true)
	Optional<RewardPlan> findByRewardCode(@Param("reward_plan_code") String planCode);
	
	
	@Query("SELECT  rp.rewardName FROM RewardPlan rp")
    List<String> findAllPlanNames();

}
