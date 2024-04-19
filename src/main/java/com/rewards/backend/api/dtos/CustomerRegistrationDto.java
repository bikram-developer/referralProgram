package com.rewards.backend.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegistrationDto {

	private String name;
	private String email;
	private String password;
//	private String mobileNumber;
	private String address;
	private String state;
	private String pincode;
	private String referralCode;
	private String parentReferralCode;
	
}
