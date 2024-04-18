package com.rewards.backend.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDashboard {
	
	private long id;
    private String name;
    private byte[] profileImg;
    private String age;
    private String gender;
    private String email;
    private String mobileNumber;
    private String address;
    @JsonAlias(value="noOfLead")
    private int referralCount;
    @JsonAlias(value="status")
    private boolean isActive;
//    private List<>
}