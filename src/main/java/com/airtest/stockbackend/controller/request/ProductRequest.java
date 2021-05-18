package com.airtest.stockbackend.controller.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

	@NotEmpty(message = "Is Empty")
	@Size(min = 2, max = 100)
	public String name;
	public String image;
	public String description;
	
}
