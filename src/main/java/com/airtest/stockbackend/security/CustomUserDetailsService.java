package com.airtest.stockbackend.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.airtest.stockbackend.model.User;
import com.airtest.stockbackend.service.UserService;

@Service					//*คลาสนี้ custom UserDetailsService ถ้ามี user จะ get ข้อมูลมา
public class CustomUserDetailsService implements UserDetailsService {
	private final UserService userService;

	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
		// TODO Auto-generated constructor stub
	}

	@Override // *โยน username เข้ามา check ว่ามีในระบบ มั้ย
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // *ส่วนในการ Ahthen
		// *มี service check user
		User user = userService.findUSerByUsername(username);
		if (user != null) {
			// *ดึงสิทธิ์
			Set<GrantedAuthority> roles = new HashSet<>();
			roles.add(new SimpleGrantedAuthority(user.getRole())); // *สร้างมา เก็บ roles

			// *สิทธิ์ แปลงเป็น list
			List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>(roles);

			// * cheack การ return
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					authority);

		} else { // *ถ้าไม่มี
			throw new UsernameNotFoundException("Username" + username + "does not exist.!");
		}
//		return null; // *ไม่ถูกการ Authen
	}
}
