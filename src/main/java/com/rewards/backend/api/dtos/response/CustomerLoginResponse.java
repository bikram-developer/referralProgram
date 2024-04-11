package com.rewards.backend.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginResponse {

	private String customerEmail;
	private String password;
	private String token;
	private String sessionId;
	private String LoginStatus;
	private String message;
}
