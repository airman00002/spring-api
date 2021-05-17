package com.airtest.stockbackend.controller.api;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airtest.stockbackend.controller.request.UserRequest;
import com.airtest.stockbackend.exception.ValidationException;
import com.airtest.stockbackend.model.User;
import com.airtest.stockbackend.service.UserService;

@RequestMapping("/auth") //* Mapping path ที่เข้ามา
@RestController
public class UserController {
	private final UserService userService;
														
	public UserController(UserService userService) {//*ฉีด service
		this.userService = userService;
		
	}
	@PostMapping("/register")						//*ส่งข้อมูลแบบ JSON
	public User register(@Valid @RequestBody UserRequest userRequest,BindingResult bindingResult) { //*ต้องมีการ Validate
		
		if(bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError ->{
				throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
			});
		}
		return userService.register(userRequest);
	}
	
	
	
}
