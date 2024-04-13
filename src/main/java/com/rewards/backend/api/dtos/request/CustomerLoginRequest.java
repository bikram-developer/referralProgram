package com.rewards.backend.api.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerLoginRequest {

	private String sessionId;
	private String ip;
	private String email;
	private String address;
	private String password;
}
