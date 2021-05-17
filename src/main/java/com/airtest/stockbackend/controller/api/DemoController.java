package com.airtest.stockbackend.controller.api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.airtest.stockbackend.until.DateUntils;

//@RestController
public class DemoController {
	

	DateUntils dateUntil;
	private SayService sayService;
	
	DemoController(DateUntils dateUntil,@Qualifier("Cat_ok")SayService sayService){
		this.dateUntil = dateUntil;
		this.sayService = sayService;
	}
	
	@GetMapping("/")
	String getToday() {
		
		return dateUntil.todayString();
	}
	
	@GetMapping("/say")
	String say() {
		
		return sayService.say();
	}
}


interface SayService{
	String say();
}

@Component("Cat_ok")
class Cat implements SayService{
// add implement method
	@Override
	public String say() {
		// TODO Auto-generated method stub
		return "Test Cat";
	}}
@Component("Dog_ok")
class Dog implements SayService{
	// add implement method
		@Override
		public String say() {
			// TODO Auto-generated method stub
			return "Test Dog";
		}}
