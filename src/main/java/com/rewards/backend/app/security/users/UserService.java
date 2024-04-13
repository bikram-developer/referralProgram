package com.rewards.backend.app.security.users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rewards.backend.exception.CustomException;

@Service
public class UserService {

	@Autowired UserRepository userRepository;
	
	@Autowired PasswordEncoder passwordEncoder;
	
	public List<UserModel> getAllUsers(){
		return userRepository.findAll();
	}
	public UserModel getDetailsUsingEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}
	public UserModel createUser(UserModel userModel) {
		userModel.setId(UUID.randomUUID().toString());
		userModel.setRoleName("SUB_ADMINS");
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		return userRepository.save(userModel);
	}
	
	public UserModel createUser(UserModel userModel, String UserName,String UserToken) {
		if(!UserName.equalsIgnoreCase("admin") && !UserToken.equalsIgnoreCase("token")) {
			throw new CustomException("Unauthorized to create admin roles");
		}
		userModel.setId(UUID.randomUUID().toString());
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		return userRepository.save(userModel);
	}
	
	
}


