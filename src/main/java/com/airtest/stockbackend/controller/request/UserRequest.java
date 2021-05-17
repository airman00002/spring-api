package com.airtest.stockbackend.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest { //* username ,password , role // ทำ validate request
	
	@NotEmpty
	@Size(min= 1 ,max = 100)
	private String username;
	
	@NotEmpty
	@Length(min = 8 , message = "The field must be at least {min} characters ") //*show ตัวเลข
	private String password;
	
	@NotEmpty
	private String role;
}
