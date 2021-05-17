package com.airtest.stockbackend.until;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;

//@Component



//@Configuration
//@Scope("prototype")
public class DateUntils {

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	
	
	public DateUntils() {
		// TODO Auto-generated constructor stub
		System.out.println("Obj Create");
	}
	
	
	@Bean
	public String todayString() {
		return simpleDateFormat.format(new Date());
	}

	@PostConstruct
	public void init() throws Exception{
		System.out.println("Obj Init");
	}
	
	@PreDestroy
	public void destroy()throws Exception{
		System.out.println("Obj Destroy");
	} 
	
}
