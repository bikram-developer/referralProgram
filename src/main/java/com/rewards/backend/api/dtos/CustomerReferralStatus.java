package com.rewards.backend.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReferralStatus {

    private int referralsMade;
    private boolean rewarded;
    private int referralsNeededForReward;
}