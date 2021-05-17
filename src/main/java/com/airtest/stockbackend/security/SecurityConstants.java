package com.airtest.stockbackend.security;

public interface SecurityConstants {
	String SECRET_KEY = "SECRET";
	String TOKEN_PREFIX = "Bearer";
	String HEADER_AUTHORIZATION = "Authorization";
	String CLAIMS_ROLE = "role";
	long EXPIRATION_TIME = (60*60*1000);  //*เวลาหมดอายุ
	
}
