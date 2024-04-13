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
	private String mobileNumber;
	private String address1;
	private String address2;
	private String state;
	private String pincode;
}
