package com.rewards.backend.app.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String email;
	private String mobileNumber;
	private String password;
	private String address1;
	private String address2;
	private String pinCode;
	private String state;
	private String activityStatus;
	private boolean isBanned;
	private boolean isLocked;
	private boolean isFreezed;
	private boolean isActive;
}
