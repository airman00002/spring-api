package com.airtest.stockbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockBackendApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(StorageService storageService) {
//		return args -> {
//			storageService.init();
//		};
//	}

}
