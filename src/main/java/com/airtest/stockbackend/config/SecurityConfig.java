package com.airtest.stockbackend.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.airtest.stockbackend.security.CustomUserDetailsService;
import com.airtest.stockbackend.security.JWTAuthenticationFilter;
import com.airtest.stockbackend.security.JWTAuthorizationFilter;

@EnableWebSecurity // *บอก class ว่าจะทำการ custom
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomUserDetailsService customUserDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	// *ฉีดการเข้ารหัส
	public SecurityConfig(CustomUserDetailsService customUserDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.customUserDetailsService = customUserDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	// *custom config ของทาง user ที่มีการเข้ารหัส
	@Override // *ระบบเก่าที่ทำ authen จะมีการทำใหม่โดย custom
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);// *มีการเข้ารหัสนะ ฉีด
																									// bcrypt เข้าไป
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // *
		http.csrf().disable().authorizeRequests() 				// *จะปิด security
				.antMatchers("/auth/register").permitAll() 			// *ยอมรับแค่ /auth/register ************
				.antMatchers(HttpMethod.DELETE, "/product/*").hasAnyAuthority("admin") // *authen ผ่าน แต่ลบได้แค่
																						// admin****
				.anyRequest().authenticated()							// * และ request อื่นต้อง authen ทั้งมหมด
				.and().exceptionHandling()								 // *จัดการ exception //*authen
				.authenticationEntryPoint((req, res, error) -> {
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED); // * จะบอก statuscode ว่าUNAUTHORIZED 401
				})
				.and()
				.addFilter(authenticationFilter()).sessionManagement()
				//*กรณีขอ resouce ต้อง addfilter
				.and()
				.addFilter(new JWTAuthorizationFilter(authenticationManager()))//*ยิง req มามี token มั้ย ถ้ามีก้อนุญาต resouce
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // *set ให้เป็น
																										// CreationPolicy
																										// STATELESS
																										// *******
	} // * ทำระบบ authen ให้ไม่มี session เลยระบุเป็น STATELESS ว่าไม่มีการสร้าง
		// session เพื่อเก็บระบบ authen
	@Bean
	UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception{//* สร้าง JWT กันนนน
	final UsernamePasswordAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager());	//*สร้าง filter มา check request ที่คนยิงมา
	//*authenticationManager must be specified Error !!
	filter.setAuthenticationManager(authenticationManager());
	return filter;
	}
	
}






