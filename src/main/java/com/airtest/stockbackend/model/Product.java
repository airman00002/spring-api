package com.airtest.stockbackend.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Setter      //@Data  //*แต่ต้อง implement code เพิ่ม
@Getter
@NoArgsConstructor  //*มีแค่ con default                   //*@AllArgsConstructor //*ไม่มี defualt เลย
@Entity   //*ทำให้เป็น Table
@Accessors(chain = true)  //* ใช้ setter return ข้อมูลออกมา
public class Product {  
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  long id; //*ทำให้เป็น primary key //IDENTITY สร้าง table ของใครของมัน 
	
	@Column(length = 150,nullable = false,unique = false) //*มีผลกับ colum เหนือ few ที่ประกาศ
	private String name;
	private String image;
	private int price;
	private int stock;
	
	//* create date
	@Setter(AccessLevel.NONE) //* ไม่สามารถ obj.setcreate date ได้
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)  //*ระบุ timestamp
	@Column(name = "create_date" , nullable = false)
	private Date createDate;
	
	@Setter(AccessLevel.NONE)
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date" , nullable = false)
	private Date updateDate;
	
	
	
	
	


}
