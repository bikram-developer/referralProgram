package com.rewards.backend.app.security.users;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="admin_users")
public class AdminUserPannelConfig{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private    long 		id; 
	private    String 		userId;
	private    BigInteger 	mobileNumber;
	private    String 		backDate;
	private    String	 	deleteAccess;
//	private    String 		passowrd;
	@JsonIgnore
	private    String 		password;
	private    String 		emailId;
	private    String 		LoginInBranch;
	private	   String 		re_print;
	private    boolean 		userStatus;
	private	   String		fullName;
	private	   String		roleType;	
}
