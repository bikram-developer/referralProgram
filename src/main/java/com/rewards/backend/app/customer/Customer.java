package com.rewards.backend.app.customer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rewards.backend.app.customer.reward.CustomerReward;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String customerId;
	
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
	
	 @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	    @JsonManagedReference // Add this annotation to prevent looping during serialization
	    private List<CustomerReward> customerRewards = new ArrayList<>();

}
