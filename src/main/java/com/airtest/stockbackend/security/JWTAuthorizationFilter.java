package com.airtest.stockbackend.security;

import static com.airtest.stockbackend.security.SecurityConstants.CLAIMS_ROLE;
import static com.airtest.stockbackend.security.SecurityConstants.HEADER_AUTHORIZATION;
import static com.airtest.stockbackend.security.SecurityConstants.SECRET_KEY;
import static com.airtest.stockbackend.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) { // *ดึงมาดูว่า Token ถูกต้องหรือเปล่า
		super(authenticationManager); //*สืบทอดเสร็จสิ้น
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//* filter ดูว่าส่งมาถูกต้อง มั้ย
		String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION); //*เหน็บมา
		//*การ set กับ filter ถ้าไม่ได้ เหน็บมาปล่อยให้ไป filter ต่อไป
		if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {//*เหน็บมามั้ย
			UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
			SecurityContextHolder.getContext().setAuthentication(authentication); 
			
			
		}
		//*ถ้าไม่ใช่ ส่งไปต่อ ในที่นี้ จะถูกส่ง ไป -> 401 || แต่ถ้ามี Bearer ต้อง check jwtต่อ -> 401 | 201
		chain.doFilter(request, response);
	}
	//*
	private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {
		//*แกะ  token
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt.replace(TOKEN_PREFIX, "")).getBody();//*คือ SECRET //การถอด token แยกกับ bearer
	
	//* get string token มี username มั้ย
		String username = claims.getSubject(); //*เหน็บ username มาใน getSubject
		if(username ==null) {
			return null;
		}
		
		//* check สิทธิ์
		ArrayList<String> roles = (ArrayList<String>) claims.get(CLAIMS_ROLE);  //*get Object มา "role" = key
		ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();  //*type
		if(roles != null) {
			for(String role : roles) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}
		return new UsernamePasswordAuthenticationToken(username,null,grantedAuthorities);// ส่ง ชื่อ/สิทธิ์
	
	} 
	
	
}



















