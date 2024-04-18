package com.rewards.backend.app.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.backend.ResponseHandler;
import com.rewards.backend.app.security.token.JwtHelper;
import com.rewards.backend.app.security.token.JwtRequest;
import com.rewards.backend.app.security.token.JwtResponse;
import com.rewards.backend.app.security.users.UserModel;
import com.rewards.backend.app.security.users.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@CrossOrigin("*")
@RestController
@RequestMapping("api/auth")
public class AuthController {

	 	@Autowired private UserDetailsService userDetailsService;
	 	
	 	 
	    @Autowired private AuthenticationManager manager;

	    @Autowired private JwtHelper helper;
	    
	    @Autowired private UserService userService;
	    
	    @PostMapping("v-3/login")
	    public ResponseEntity<Object> login(@RequestBody JwtRequest request) {
	        this.doAuthenticate(request.getEmail(), request.getPassword());
	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        String token = this.helper.generateToken(userDetails);
	        JwtResponse response = JwtResponse.builder()
	                .jwtToken(token)
	                .username(userDetails.getUsername()).build();
	        
	        return ResponseHandler.generateResponse(response, HttpStatus.OK, "Success");
	    }
	    
	    
	    @PostMapping("/v-2/login")
	    public ResponseEntity<Object> loginV2(@RequestBody JwtRequest request) {
	    	try {

		        this.doAuthenticate(request.getEmail(), request.getPassword());
		        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

		        // Additional claims for v-2/login
		        Map<String, Object> customClaims = new HashMap<>();
		        UserModel userModel = userService.getDetailsUsingEmail(request.getEmail());
 //		        customClaims.put("branchAccessList", userModel.getBranchAccessList());
		        customClaims.put("userId", userModel.getUserId());

		        String token = helper.generateTokenWithClaimsWithoutSub(customClaims, userDetails.getUsername());
		        JwtResponse response = JwtResponse.builder()
		                .jwtToken(token)
		                .username(userDetails.getUsername()).build();

		        return ResponseHandler.generateResponse(response, HttpStatus.OK, "Success");
		    
			} catch (Exception e) {
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, "Success");
			}
	    }
	    
	    
	    
	    
	    
	    @PostMapping("/login")
	    public ResponseEntity<Object> loginV3(@RequestBody JwtRequest request) {
	        try {
	            this.doAuthenticate(request.getEmail(), request.getPassword());
	            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

	            Map<String, Object> customClaims = new HashMap<>();
	            UserModel userModel = userService.getDetailsUsingEmail(request.getEmail());

	            customClaims.put("userId", userModel.getUserId());
	            String token = helper.generateTokenWithClaimsWithoutSub(customClaims, userDetails.getUsername());
	            JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();

	            return ResponseHandler.generateResponse(response, HttpStatus.OK, "Success");

	        } catch (BadCredentialsException | UsernameNotFoundException ex) {
	            ex.printStackTrace();
	            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, "Authentication failed");
	        } catch (SignatureException exc) {
	            exc.printStackTrace();
	            return ResponseHandler.generateResponse("Invalid JWT signature", HttpStatus.UNAUTHORIZED, "Authentication failed");
	        } catch (ExpiredJwtException excep) {
	        	excep.printStackTrace();
	            return ResponseHandler.generateResponse("JWT token expired", HttpStatus.UNAUTHORIZED, "Authentication failed");
	        } catch (MalformedJwtException except) {
	        	except.printStackTrace();
	            return ResponseHandler.generateResponse("Invalid JWT token", HttpStatus.UNAUTHORIZED, "Authentication failed");
	        } catch (Exception excepti) {
	        	excepti.printStackTrace();
	            return ResponseHandler.generateResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process the request");
	        }
	    }

	    private void doAuthenticate(String email, String password) throws BadCredentialsException {
	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	            manager.authenticate(authentication);
	        } catch (BadCredentialsException | UsernameNotFoundException e) {
	            throw new BadCredentialsException("Invalid Username or Password");
	        } catch (Exception e) {
	        	throw new BadCredentialsException("Invalid Credentials");
			}
	    }
	    
//	    @PostMapping("/login")
//	    public ResponseEntity<Object> loginV3(@RequestBody JwtRequest request) {
//	    	try {
//
//		        this.doAuthenticate(request.getEmail(), request.getPassword());
//		        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//
//		        // Additional claims for v-2/login
//		        Map<String, Object> customClaims = new HashMap<>();
//		        UserModel userModel = userService.getDetailsUsingEmail(request.getEmail());
//		        customClaims.put("userMainBranch", userModel.getUserMainBranch());
//
//		        List<Map<String,String>> branchAccessList = new ArrayList<>();
//		        branchAccessList = tempServiceClass.getBranchAccessList(request);
//		        customClaims.put("branchAccessList", branchAccessList);
//		        customClaims.put("userId", userModel.getUserId());
//		        customClaims.put("userMainBranch", userModel.getUserMainBranch());
//		        String token = helper.generateTokenWithClaimsWithoutSub(customClaims, userDetails.getUsername());
//		        JwtResponse response = JwtResponse.builder().jwtToken(token)
//		                .username(userDetails.getUsername()).build();
//
//		        return ResponseHandler.generateResponse(response, HttpStatus.OK, "Success");
//		    
//			} catch(BadCredentialsException e) {
//				e.printStackTrace();
//				return ResponseHandler.generateResponse(" Invalid Username or Password  !!", HttpStatus.UNAUTHORIZED, "Authentication failed");
//			}
//	    	catch (SignatureException e) {
//	            e.printStackTrace();
//	            return ResponseHandler.generateResponse("Invalid JWT signature", HttpStatus.UNAUTHORIZED, "Authentication failed");
//	        } catch (ExpiredJwtException e) {
//	            return ResponseHandler.generateResponse("JWT token expired", HttpStatus.UNAUTHORIZED, "Authentication failed");
//	        } catch (MalformedJwtException e) {
//	            return ResponseHandler.generateResponse("Invalid JWT token", HttpStatus.UNAUTHORIZED, "Authentication failed");
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return ResponseHandler.generateResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process the request");
//	        }
//	    }
//	    private void doAuthenticate(String email, String password) throws BadCredentialsException{
//	    	
//	    	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
//	    	try {
//	    		manager.authenticate(authentication);
//	    	} catch (BadCredentialsException e) {
//	    		throw new BadCredentialsException(" Invalid Username or Password  !!");
////	            throw new CustomException(" Invalid Username or Password  !!");
//	    	}
//	    }
	    
	    public JwtResponse loginHelper(@RequestBody JwtRequest request) {
	        this.doAuthenticate(request.getEmail(), request.getPassword());
	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        String token = this.helper.generateToken(userDetails);
	        return JwtResponse.builder()
	                .jwtToken(token)
	                .username(userDetails.getUsername()).build();
	    }

	    @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity<?> exceptionHandler() {
	        return ResponseHandler.generateResponse("Credentials Invalid !!", HttpStatus.BAD_REQUEST, "FAILEED");
	    }
	    
	    @PostMapping("/createUser")
	    public ResponseEntity<?> createUser(@RequestBody UserModel user
	    		) {
	    		try {
	    			return ResponseHandler.generateResponse(userService.createUser(user), HttpStatus.OK, "Success");
	    		}catch(Exception e) {
	    			return ResponseHandler.generateResponse(e.toString(), HttpStatus.BAD_REQUEST, "Failed");
	    		}
	    }
	    
	    @GetMapping("/getall") 
	    public ResponseEntity<?> getall(){
	    	try {
    			return ResponseHandler.generateResponse(userService.getAllUsers(), HttpStatus.OK, "Success");
    		}catch(Exception e) {
    			return ResponseHandler.generateResponse("FAILEED", HttpStatus.BAD_REQUEST, "Failed");
    		}
	    }
	    
		 
}
