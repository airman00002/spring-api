package com.airtest.stockbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.airtest.stockbackend.controller.request.ProductRequest;
import com.airtest.stockbackend.exception.ProductNotFoundException;
import com.airtest.stockbackend.model.Product;
import com.airtest.stockbackend.repository.ProductRepository;

@Service // * ชั้น service
public class ProductServicempl implements ProductService {
	private StorageService storageService;
	private ProductRepository productRepository;

	ProductServicempl(StorageService storageService, ProductRepository productRepository) { // * productRepository
		this.storageService = storageService;
		this.productRepository = productRepository;
	}
	// *-----Get-------------------------------------------------------------------------------------------

	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")); // * select *

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
		String fileName = storageService.store(productRequest.getImage());
		Product data = new Product();
		data.setName(productRequest.getName()).setImage(fileName).setPrice(productRequest.getPrice())
				.setStock(productRequest.getStock());

		return productRepository.save(data); // *ทำเสจ return กลับมาด้วย
	}

	// *-----PUT------------------------------------------------------------------------------------
	@Override
	public Product updateProduct(ProductRequest productRequest, Long id) {
		String fileName = storageService.store(productRequest.getImage());

		Optional<Product> product = productRepository.findById(id); // *อาจเป็น null
		if (product.isPresent()) {
			Product editProduct = product.get(); // *จะเปลี่ยนแปลงค่า
			if (fileName != null) {
				editProduct.setImage(fileName);
			}

			editProduct.setName(productRequest.getName()).setPrice(productRequest.getPrice())
					.setStock(productRequest.getStock());
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

	@Override
	public List<Product> getProductByNameAndStock(String name, int stock) {
		return productRepository.findByNameContainingAndStockGreaterThanOrderByStockDesc(name, stock);
	}

	@Override
	public List<Product> getProductOutOfStock() {
		return productRepository.checkOutOfStock();
	}
	@Override
	public List<Product> getProductByNameAndPrice(String name, int price) {
		return productRepository.searchNameAndPrice(name, price);
	}

}
