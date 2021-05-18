package com.airtest.stockbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.airtest.stockbackend.controller.request.ProductRequest;
import com.airtest.stockbackend.exception.ProductNotFoundException;
import com.airtest.stockbackend.model.Product;
import com.airtest.stockbackend.repository.ProductRepository;

@Service // * ชั้น service
public class ProductServicempl implements ProductService {
	private ProductRepository productRepository;

	ProductServicempl( ProductRepository productRepository) { // * productRepository
		this.productRepository = productRepository;
	}
	// *-----Get-------------------------------------------------------------------------------------------

	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
		//		return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")); // * select *

	}
	// *-----Get---By---ID------------------------------------------------------------------------------------------

	@Override
	public Product getProductById(Long id) {
		Optional<Product> product = productRepository.findById(id); // *อาจเป็น null
		if (product.isPresent()) {
			return product.get();
		}
		throw new ProductNotFoundException(id);
	}
	// *-----Post-------------------------------------------------------------------------------------------

	@Override
	public Product createProduct(ProductRequest productRequest) {
		Product data = new Product();
		data.setName(productRequest.getName())
		.setImage(productRequest.getImage())
		.setDescription(productRequest.getDescription());

		return productRepository.save(data); // *ทำเสจ return กลับมาด้วย
	}

	// *-----PUT------------------------------------------------------------------------------------
	@Override
	public Product updateProduct(ProductRequest productRequest, Long id) {
		Optional<Product> product = productRepository.findById(id); // *อาจเป็น null
		if (product.isPresent()) {
			Product editProduct = product.get(); // *จะเปลี่ยนแปลงค่า
			
			editProduct.setName(productRequest.getName())
						.setImage(productRequest.getImage())
						.setDescription(productRequest.getDescription());
			
			return productRepository.save(editProduct);
		}
		throw new ProductNotFoundException(id);
	}

	// *-----DELETE---------------------------------------------------------------------------------
	@Override
	public void deleteProduct(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (Exception e) {
			throw new ProductNotFoundException(id);
		}

	}
	// *-----Search-------------------------------------------------------------------------------------------

	@Override
	public Product getProductByName(String name) {
		Optional<Product> product = productRepository.findTopByName(name); // *อาจเป็น null
		if (product.isPresent()) {
			return product.get();
		}
		throw new ProductNotFoundException(name);
	}



}
