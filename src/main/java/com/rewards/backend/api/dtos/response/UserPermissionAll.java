package com.rewards.backend.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionAll {

	private long id;
	private String name;
	private String email;
	// user account banned or not
	private boolean isBanned;
	// account login disabled
	private boolean isLocked;
	// is the account freezed or not, can perform any tasks or not
	private boolean isFreezed;
	//is the account active or not
	private boolean isActive;
	// can referrer someone
	private boolean referralStatus;
	// can take out rewards or not
	private boolean rewardAccess;
	
}
