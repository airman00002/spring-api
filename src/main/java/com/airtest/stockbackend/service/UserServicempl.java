package com.airtest.stockbackend.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.airtest.stockbackend.controller.request.UserRequest;
import com.airtest.stockbackend.exception.UserDuplicateException;
import com.airtest.stockbackend.model.User;
import com.airtest.stockbackend.repository.UserRepository;

@Service // *สามารถฉีดที่ class อื่นได้
public class UserServicempl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServicempl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {// * bcrypt **แต่ต้อง Config Bean ก่อน*****
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;

	}

	@Override
	public User register(UserRequest userRequest) {
		User user = userRepository.findByUsername(userRequest.getUsername());
		if (user == null) {
			// *ไม่มีuser กด้ สร้าง และadd
			user = new User().setUsername(userRequest.getUsername())
					.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()))
					.setRole(userRequest.getRole());
			return userRepository.save(user);
		}
		// * throw ออกไป
		throw new UserDuplicateException(userRequest.getUsername());
	}

	@Override
	public User findUSerByUsername(String username) { //*อาจยังไม่มี user <Optional> //*ส่งusername เข้าไป check
	 Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));	
	 //*มีค่าหรือป่าว
	 if(user.isPresent()) {
		 return user.get();
	 }
		return null;
	}

}
