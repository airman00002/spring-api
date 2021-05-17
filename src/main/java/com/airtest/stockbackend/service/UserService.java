package com.airtest.stockbackend.service;

import com.airtest.stockbackend.controller.request.UserRequest;
import com.airtest.stockbackend.model.User;

public interface UserService  {
	//*ทำระบบ register
	User register(UserRequest userRequest);
	
	User findUSerByUsername(String username);
}
