package com.airtest.stockbackend.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.airtest.stockbackend.controller.request.ProductRequest;
import com.airtest.stockbackend.exception.ProductNotFoundException;
import com.airtest.stockbackend.exception.ValidationException;
import com.airtest.stockbackend.model.Product;
import com.airtest.stockbackend.service.ProductService;

@RestController
@RequestMapping("/product")
//@CrossOrigin()
//@Slf4j // *การทำ log จาก lombok
public class ProductController {

//	@RequestMapping(path = "/say",method = RequestMethod.GET)

//	private final AtomicLong counter = new AtomicLong(); // gen ค่า
//	private List<Product> products = new ArrayList<>();
//	private static final Logger log = LoggerFactory.getLogger(ProductController.class);// * สร้าง log สำหรับ
	// productcontroller
	// *constructure

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;

	}
	// *-----Get-------------------------------------------------------------------------------------------

	@GetMapping()
	public List<Product> getProducts() {
		return productService.getAllProduct();
	}

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable long id) {
		return productService.getProductById(id);
	}

	// *-----Post-------------------------------------------------------------------------------------------

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	public Product addProduct(@Valid ProductRequest productRequest, BindingResult bindingResult) {// *โยนไฟล์เข้าไปด้วย

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> { // *ถ้า error เช็ค field ไหนที่ error
				throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
			});
		}
		return productService.createProduct(productRequest);

	}

	// *-----PUT------------------------------------------------------------------------------------
	@PutMapping("/{id}")
	public Product editProduct(@Valid ProductRequest productRequest, BindingResult bindingResult,
			@PathVariable long id) {
		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> { // *ถ้า error เช็ค field ไหนที่ error
				throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
			});
		}
		return productService.updateProduct(productRequest, id);
	}

//*-----DELETE---------------------------------------------------------------------------------
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable long id) {
		productService.deleteProduct(id);
	}
	
//*---ETC-------------------------------------------------------------------------------------------
	@GetMapping(path = "/search", params = "name") // * path เหมือน params ไม่เหมือน
	public Product searchProductByName(@RequestParam String name) {
		return productService.getProductByName(name);
	}

	@GetMapping(path = "/search", params = { "name", "stock" }) // * path เหมือน params ไม่เหมือน
	public List<Product> searchProductByNameAndStock(@RequestParam String name, @RequestParam int stock) {
		return productService.getProductByNameAndStock(name, stock);
	}
	
	@GetMapping(path = "/out-of-stock") // * path เหมือน params ไม่เหมือน
	public List<Product> checkOutOfStock() {
		return productService.getProductOutOfStock();
	}
	
	@GetMapping(path = "/search", params = { "name", "price" }) // * path เหมือน params ไม่เหมือน
	public List<Product> searchNameAndPrice(@RequestParam String name, @RequestParam int price) {
		return productService.getProductByNameAndPrice(name, price);
	}

//*--------------------------------------------------------------------------------------------------------------------------------------------------------	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String handlerProductNotFound(ProductNotFoundException ex) {
		return ex.getMessage();
	}

	// ../say/1234/name/{...}
//	@GetMapping({ "/say/{id}/name/{name}", "/sayname/{id}" })
//	public String getProductByName(@PathVariable(value = "id") long id, @PathVariable(required = false) String name) {
//		return "Say GETProductById :  " + name + ": id :" + id;
//	}

	// ../say/print?name=..........
//	@GetMapping("/say/print")
//	public String getProductByNameQuery(@RequestParam(name = "name",required = false,defaultValue = "defaultValue") String name ) {
//		return "Say QueryString Name : " +name ;
//	}

}
