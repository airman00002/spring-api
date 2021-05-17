package com.airtest.stockbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) { //*แต่ไม่ได้ register ในส่วนของ spring container เลยต้องประกาศ annotation ข้างบน
//		registry.addMapping("/**"); //*ยอมรับทุก path ของ controller (CORS)
		registry.addMapping("/product/*").allowedMethods("*");//* ทุก Method
//		registry.addMapping("product").allowedMethods("GET","POST");//* เจาะจงอย่างใดอย่างหนึ่ง
		
		
		
	}
}
