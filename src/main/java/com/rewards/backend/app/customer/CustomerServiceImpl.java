package com.rewards.backend.app.customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewards.backend.api.dtos.CustomerReferralStatus;
import com.rewards.backend.api.dtos.CustomerRegistrationDto;
import com.rewards.backend.api.dtos.mapperClass.MapperClass;
import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.api.dtos.request.UserPermissionUpdateRequest;
import com.rewards.backend.api.dtos.response.CustomerLoginResponse;
import com.rewards.backend.api.dtos.response.UserPermissionAll;
import com.rewards.backend.exception.CustomException;
import com.rewards.backend.exception.CustomerNotFoundException;
import com.rewards.backend.exception.CustomerRegistrationException;
import com.rewards.backend.exception.CustomerStatusInactiveException;
import com.rewards.backend.exception.InvalidEmailException;
import com.rewards.backend.exception.InvalidPhoneNumberException;
import com.rewards.backend.exception.ReferrerAlreadyExistsException;
import com.rewards.backend.utility.RandomNumberGenerator;
import com.rewards.backend.validator.CustomerValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService{

	private final CustomerRepo customerRepo;
	@Autowired private MapperClass mapperClass;
	
	private static final String FAILED = "Failed to verify user";
	public CustomerServiceImpl (CustomerRepo customerRepo) {
		this.customerRepo= customerRepo;
	}

	@Override
	public CustomerLoginResponse customerLogin(CustomerLoginRequest inputs) {

		try {
			String email = inputs.getEmail();
			String password = inputs.getPassword();
					
			String db_pass  = customerRepo.getPassword(email);
			int count = customerRepo.countNoOfActiveEmails(email);
			System.out.println("email "+ email);
			System.out.println("count "+ count);
			if(count!=1)
			{
				throw new CustomerNotFoundException("user not found");
			}
			if(customerRepo.getCustomerFromDbIfTrue(email,password) == null)
			{
				throw new CustomerNotFoundException(FAILED);
			}
			else {
				
				if(!customerRepo.CustomerStatus(email) && customerRepo.loginStatus(email)) {
					throw new CustomerStatusInactiveException("""
							access denied, you have been locked out of the account, please contact the ADMIN to resolve
								""");
				}
				
				Customer customer = customerRepo.getCustomerFromDbIfTrue(email, password);
            CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
            customerLoginResponse.setCustomerEmail(customer.getEmail());
            customerLoginResponse.setLoginStatus(customer.isActive());
            customerLoginResponse.setMessage(email + " successfull Login");
            customerLoginResponse.setName(customer.getName());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            Date nextDay = calendar.getTime();
            String sessionId = dateFormat.format(currentDate) + " till " + dateFormat.format(nextDay);
            customerLoginResponse.setSessionId(sessionId);
            
            customerLoginResponse.setToken(null);
            return customerLoginResponse;
            }
				
		}	catch (CustomerStatusInactiveException csi) {
			throw new CustomException("customer Status inactive");
		}
		catch (CustomerNotFoundException cnfe) {
			throw new CustomException("Customer Not Found");
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(FAILED);
		}
	}

	@Override
    public void addReferrer(Long customerId, Long referrerId) {
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (customer.getReferrerId() == null) {
                customer.setReferrerId(referrerId);
                customerRepo.save(customer);
            } else {
                throw new ReferrerAlreadyExistsException("Customer already has a referrer");
            }
        } else {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }
    }
	
	@Override
	public void customerRegister(CustomerRegistrationDto entity) {
		checkEmail(entity.getEmail());
//		checkPhoneNumber(entity.getMobileNumber());
		register(entity);
	}

	private Customer register(CustomerRegistrationDto entity) {
		Customer customer = MapperClass.toCustomer(entity);
		customer.setActive(true);
		customer.setBanned(false);
		customer.setFreezed(false);
		customer.setLocked(false);
		customer.setProfileImg(null);
		customer.setReferralCode(generateReferralCode());
		return customerRepo.saveAndFlush(customer);
	}

	private void checkPhoneNumber(String number) {
	    if (!CustomerValidator.isValidPhoneNumber(number)) {
	        throw new InvalidPhoneNumberException("Invalid phone number format");
	    }
	    if (numberAlreadyExists(number)) {
	        throw new CustomerRegistrationException("Phone number already exists");
	    }
	}

	private boolean numberAlreadyExists(String number) {
//		 return (customerRepo.countNoOfNumberPresent(number)!=0) ? true : false;
		 return true;
	}

	private void checkEmail(String email) {
	    if (!CustomerValidator.isValidEmail(email)) {
	        throw new InvalidEmailException("Invalid email format");
	    }
	    if (emailAlreadyExists(email)) {
	        throw new CustomerRegistrationException("Email already exists");
	    }
	}

	private boolean emailAlreadyExists(String email) {
		return (customerRepo.countNoOfActiveEmails(email) != 0 ) ? true : false;
	}

	@Override
	public List<Customer> getAllCustomer(HttpServletRequest request) {
		try {
			
			return customerRepo.findAll();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	private String generateReferralCode() {
		return RandomNumberGenerator.generate6DigitCode().toString();
	}
	
	 @Override
	    public void referCustomer(String referralCode, Long referrerId) {
	        Customer referredCustomer = customerRepo.findByReferralCode(referralCode);
	        if (referredCustomer != null && referredCustomer.getReferrerId() == null) {
	            referredCustomer.setReferrerId(referrerId);
	            customerRepo.save(referredCustomer);
	            // Increment referral count for the referrer
	            Optional<Customer> customer = customerRepo.findById(referrerId);
	            if(!customer.isPresent()) {throw new CustomerNotFoundException("Customer with ID " + referrerId + " not found");}
	            Customer referrer = customer.get();
	            referrer.setReferralCount(referrer.getReferralCount() + 1);
	            customerRepo.save(referrer);
	        }
	    }
	 
	 

	 @Override
	 public List<CustomerReferralStatus> getReferralStatus(Long customerId) {
	     Optional<Customer> customerOptional = customerRepo.findById(customerId);
	     if (customerOptional.isPresent()) {
	         Customer customer = customerOptional.get();
	         List<CustomerReferralStatus> referralStatusList = new ArrayList<>();
	         int referralsMade = customer.getReferralCount();
	         int rewardThreshold = 5; // Change this value as needed
	         
	         // Iterate through the referral quotas and create CustomerReferralStatus objects
	         while (referralsMade > 0) {
	             boolean rewarded = customer.isRewarded();
	             int referralsNeededForReward = Math.max(0, rewardThreshold - referralsMade);
	             referralStatusList.add(new CustomerReferralStatus(referralsMade, rewarded, referralsNeededForReward));
	             
	             // Move to the previous quota
	             referralsMade -= rewardThreshold;
	             rewardThreshold = 5; // Reset reward threshold for subsequent quotas
	         }
	         return referralStatusList;
	     } else {
	         throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
	     }
	 }

	    
	    @Override
	    public void customerRegisterWithReferral(CustomerRegistrationDto entity) {
	        checkEmail(entity.getEmail());
//	        checkPhoneNumber(entity.getMobileNumber());
	        Customer customer = register(entity);
	        String parentReferralCode = entity.getParentReferralCode();
	        if (parentReferralCode != null && !parentReferralCode.isEmpty()) {
	            applyReferral(customer, parentReferralCode);
	        }
	    }

//	    private void applyReferral(Customer customer, String parentReferralCode) {
//	        Customer parentReferrer = customerRepo.findByReferralCode(parentReferralCode);
//	        if (parentReferrer != null) {
//	            customer.setReferrerId(parentReferrer.getId());
//	            customerRepo.save(customer);
//	        }
//	    }
	    
	    private void applyReferral(Customer customer, String parentReferralCode) {
	        Customer parentReferrer = customerRepo.findByReferralCode(parentReferralCode);
	        if (parentReferrer != null) {
	            // Set the referrer ID for the new customer
	            customer.setReferrerId(parentReferrer.getId());
	            
	            // Increment the parent referrer's referral count
	            parentReferrer.setReferralCount(parentReferrer.getReferralCount() + 1);
	            
	            // Save both the new customer and the parent referrer
	            customerRepo.save(customer);
	            customerRepo.save(parentReferrer);
	        }
	    }

		@Override
		public String getReferralCode(String email) {
			try {
				String referralCode = customerRepo.getCustomerReferralCode(email).orElse(null);
				return referralCode;
			} catch (Exception e) {
				throw new CustomException("Failed to view customer data");
			}
		}

		@Override
		public Customer getById(String customerId) {
			return customerRepo.findById(Long.parseLong(customerId)).get();
		}
		
		@Override
		public List<UserPermissionAll> getAllUserPermission(HttpServletRequest request) {
			try {
				List<Customer> customerList = customerRepo.findAll();
				List<UserPermissionAll> responseList = new LinkedList<>(); 
				for(Customer customer : customerList) {
					responseList.add(mapperClass.toUserPermission(customer));
				}
				return responseList;
			} catch (Exception e) {
				throw new CustomException(FAILED);
			}
		}
		
		@Override
		@Transactional
		public UserPermissionAll updateUserPermission(long userId, UserPermissionUpdateRequest request) {
		    try {
		        // Retrieve customer by ID
		        Customer customer = customerRepo.findById(userId)
		                .orElseThrow(() -> new CustomerNotFoundException("User not found"));
		        System.err.println("Old Customer from db fetched "+ customer.toString());
		        // Update customer permission based on the request
		        customer.setBanned(request.isBanned());
		        customer.setLocked(request.isLocked());
		        customer.setFreezed(request.isFreezed());
		        customer.setActive(request.isActive());
		        customer.setReferralStatus(request.isReferralStatus());
		        customer.setRewardAccess(request.isRewardAccess());
		        System.err.println("Old customer after updating the values "+ customer.toString());
		        // Save the updated customer
		        Customer savedCustomer = customerRepo.save(customer);
		        System.err.println("After saving the customer " + savedCustomer.toString());
		        return mapperClass.toUserPermission(savedCustomer);
		    } catch (CustomerNotFoundException e) {
		        throw e;
		    } catch (Exception e) {
		        throw new CustomException("Failed to update user permission");
		    }
		}

}
