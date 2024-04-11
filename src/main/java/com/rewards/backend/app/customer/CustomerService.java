package com.rewards.backend.app.customer;

import com.rewards.backend.api.dtos.request.CustomerLoginRequest;
import com.rewards.backend.api.dtos.response.CustomerLoginResponse;

public interface CustomerService {

	CustomerLoginResponse customerLogin(CustomerLoginRequest inputs);

}
