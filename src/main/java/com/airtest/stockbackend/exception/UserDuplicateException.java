package com.airtest.stockbackend.exception;

public class UserDuplicateException extends RuntimeException {
	public UserDuplicateException(String username){ //*กรณีมี username อยู่แล้ว
		super("Username" + username + " already exists.! ");
	}

	
}
