package com.rewards.backend.api.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EachCustomerLeadData {

	private String name;
	private String email;
	private String mobileNumber;
    private String age;
    private String gender;
    
    private String address;
	private String pinCode;
	private String state;
	private String activityStatus;
	private boolean isBanned;
	private boolean isLocked;
	private boolean isFreezed;
	private boolean isActive;
	
	private String referralCode;
	private Long referrerId;
	private int referralCount;
	private boolean rewarded;
	
    private byte[] profileImg;
    
    private List<CustomerDashboard> referralList;
}
