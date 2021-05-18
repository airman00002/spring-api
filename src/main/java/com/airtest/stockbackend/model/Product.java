package com.airtest.stockbackend.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Setter      //@Data  //*แต่ต้อง implement code เพิ่ม
@Getter
@NoArgsConstructor  //*มีแค่ con default     //*@AllArgsConstructor //*ไม่มี defualt เลย
@Entity   //*ทำให้เป็น Table
@Accessors(chain = true)  //* ใช้ setter return ข้อมูลออกมา
public class Product {  
	
	public @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  long id; //*ทำให้เป็น primary key 
																	//IDENTITY สร้าง table ของใครของมัน 
	
	@Column(length = 150,nullable = false,unique = false) //*มีผลกับ colum เหนือ few ที่ประกาศ
	public String name;
	public String image;
	public String description;

	

	
	
	
	


}
