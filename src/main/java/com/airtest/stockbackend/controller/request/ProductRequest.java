package com.airtest.stockbackend.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

	@NotEmpty(message = "Is Empty")
	@Size(min = 2, max = 100)
	private String name;
	private MultipartFile image;
	private int price;
	private int stock;

}
