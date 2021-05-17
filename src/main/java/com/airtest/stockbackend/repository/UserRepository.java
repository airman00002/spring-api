package com.airtest.stockbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airtest.stockbackend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	//* select * from user where username = 'test'

	User findByUsername(String username);

}
