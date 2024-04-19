package com.rewards.backend.app.customer;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
    private String age;
    private String gender;

	private String password;
	private String address;
	private String pinCode;
	private String state;
	private String activityStatus;
	private boolean isBanned;
	private boolean isLocked;
	private boolean isFreezed;
	private boolean isActive;
	
	private boolean referralStatus;
	
	private boolean rewardAccess;
	
	private String referralCode;
	private Long referrerId;
	private int referralCount;
	private boolean rewarded;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
    private byte[] profileImg;
}
