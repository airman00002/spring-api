package com.airtest.stockbackend.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.airtest.stockbackend.controller.request.ProductRequest;
import com.airtest.stockbackend.exception.ProductNotFoundException;
import com.airtest.stockbackend.model.Product;
import com.airtest.stockbackend.service.ProductService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200/**")
@RequestMapping("/product")
@CrossOrigin()
//@Slf4j // *การทำ log จาก lombok
public class ProductController {

//	@RequestMapping(path = "/say",method = RequestMethod.GET)

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;

	}
	// *-----Get-------------------------------------------------------------------------------------------

	@GetMapping()
	public List<Product> getProducts() {
		return productService.getAllProduct();
	}

	@GetMapping("/getById/{id}")
	public Product getProductById(@PathVariable long id) {
		return productService.getProductById(id);
	}

	// *-----Post-------------------------------------------------------------------------------------------

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	public Product addProduct(@RequestBody ProductRequest productRequest) {// *โยนไฟล์เข้าไปด้วย

		return productService.createProduct(productRequest);

	}

	// *-----PUT------------------------------------------------------------------------------------
	@PutMapping("/update/{id}")
	public Product editProduct(@RequestBody ProductRequest productRequest, @PathVariable long id) {

		return productService.updateProduct(productRequest, id);
	}

//*-----DELETE---------------------------------------------------------------------------------
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void deleteProduct(@PathVariable long id) {
		productService.deleteProduct(id);
	}

//*--------------------------------------------------------------------------------------------------------------------------------------------------------	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String handlerProductNotFound(ProductNotFoundException ex) {
		return ex.getMessage();
	}

}
