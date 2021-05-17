package com.airtest.stockbackend.security;

import static com.airtest.stockbackend.security.SecurityConstants.CLAIMS_ROLE;
import static com.airtest.stockbackend.security.SecurityConstants.EXPIRATION_TIME;
import static com.airtest.stockbackend.security.SecurityConstants.SECRET_KEY;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.airtest.stockbackend.controller.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//* class นี้ จะทำการ implement เมื่อยิงไปยัง path login > req body(Json) มา match 
//*  >AuthenticationManager ตรวจสอบ username password > return ออกไป --> Gen token กันต่อ !!!  

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter { // * เป็น Security ของ Spring Boot

	private final AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) { // *เป็นตัวในการจัดการ login / และ
																					// username password
		this.authenticationManager = authenticationManager; // *จะ filter path /auth/login
		// *path ที่ทำการ check ว่าให้ match กับ path อะไร
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));// *แบบ POST
																										// มีผลเมื่อยิง
																										// request มา

	}

	// *เมื่อ path match แล้วจะทำตรงนี้
	@Override // *สามารถ request และ response กลับไปได้ด้วย
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException { // *เป็น exception เลยต้อง try catch
		try {
			// *รับมาก่อนจาก request ดึง requestมา แต่ต้อง Mapping ก่อน //เพราะเมื่อส่ง JSON
			// มาจะmapping กับObjectMapper
			UserRequest userRequest = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);

			// *ต้อง return obj ออกไป
			return authenticationManager.authenticate( // *ข้อมูลที่ pass ต้องโยนมาจาก request คือ ส่งusername ,password
														// เพื่อ authen
					new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword(),
							new ArrayList<>()));
		} catch (IOException ex) {
			throw new RuntimeException(ex); // *โยนออกไป
		}
	}

	// *เข้ามาเมื่อ Authen ผ่าน Gen JWT !!
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		if (authResult.getPrincipal() != null) { // *ทำการ catch //*หลังจาก catch จะได้ username ,roles
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
					.getPrincipal();
			// *เกิดจากการ CustomUserDetails...
			// * ทำ JWT ..username ,roles **ติดตั้ง Dependency****
			String username = user.getUsername();
			if (username != null && username.length() > 0) {
				Claims claims = Jwts.claims().setSubject(username) // *Sub name มาตรฐาน
						.setIssuer("Air_krub") // * ผู้ออก
						.setAudience("www.google.com"); // *ผู้ชมเข้ามาดู
				// * set roles เป็น Array
				List<String> roles = new ArrayList<>();
				user.getAuthorities().stream().forEach(authority -> roles.add(authority.getAuthority()) // *get roles
																										// ออกมา
				);
				// * roles เข้าไป //*จากหน้า SecurityContants**********************
				claims.put(CLAIMS_ROLE, roles); // *put เข้าpayload
				claims.put("value", "Airman");
				// *gen token จะ response ไปหา user request มา *** HttpServletResponse
				// //รูปแบบในการส่ง
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				// *สร้างตัว json string
				Map<String, Object> responseJSON = new HashMap<>();
				responseJSON.put("token", createToken(claims)); // *ต้อง ทำการ base 64 แล้วencode || สร้าง Fn//
																// createToken
				// *return ออกไป
				OutputStream out = response.getOutputStream();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writerWithDefaultPrettyPrinter().writeValue(out, responseJSON);

				out.flush();
				//* ได้ Token มา ************
			}
		}

	}

	// * createToken
	private String createToken(Claims claims) {
		return Jwts.builder().setClaims(claims) // *ได้ตัว payload
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256,SECRET_KEY) //*เวลาการจัดการ Token
				.compact();// *กระบวนการ biuld

	}

}
