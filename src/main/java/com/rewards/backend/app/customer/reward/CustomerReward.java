package com.rewards.backend.app.customer.reward;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rewards.backend.app.customer.Customer;
import com.rewards.backend.app.referral.referral.RewardPlan;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference // Add this annotation to prevent looping during serialization
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "reward_plan_id", nullable = false)
    private RewardPlan rewardPlan;

    private LocalDate assignmentDate; 
    private LocalDate expirationDate; 
    private boolean expired; 
    private boolean claimed; 
}
