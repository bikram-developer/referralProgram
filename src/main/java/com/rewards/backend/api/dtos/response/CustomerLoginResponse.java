package com.rewards.backend.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginResponse {

	private String name;
	private String customerEmail;
	private String token;
	private String sessionId;
	private boolean LoginStatus;
	private String message;
}
