package com.rewards.backend.app.referral.referral;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardPlan {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String rewardName;
    private String rewardType;
    private String rewardCode;
    private String rewardCouponCode;
    private String rewardValue;
    private String rewardPoints;
    private int expirationDate; 
    private boolean hasExpiration;
}
