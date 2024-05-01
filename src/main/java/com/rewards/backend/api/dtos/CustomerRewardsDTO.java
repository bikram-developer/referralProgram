package com.rewards.backend.api.dtos;

import java.time.LocalDate;

import com.rewards.backend.app.customer.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRewardsDTO {
    private Customer customer;
    private long id;
    private LocalDate assignmentDate;
    private LocalDate expirationDate;
    private boolean expired;
    private boolean claimed;
}