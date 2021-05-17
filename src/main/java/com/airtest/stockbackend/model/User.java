package com.airtest.stockbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true) //* setter ให้แต่ละ field และ return obj ปัจจุบันออกมา ** สามารถ .set(.get) ได้เรื่อยๆๆๆๆ ****
@Table(name = "`user`") //*ชื่อไปซ้ำกัน เลย กำหนดให้ชัดเจน
public class User {
	// *สร้าง primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//* user ต้องไม่ว่าง และไม่ซ้ำ
	@Column(nullable = false, unique = true)
	private String username;
	
	//*password
	@Column(nullable = false,unique = true)
	private String password;
	
	//* การให้สิทธิ์ ต้องไม่ว่าง
	@Column(nullable = false)
	private String role;
	
}
