package com.airtest.stockbackend.service;

import java.util.List;

import com.airtest.stockbackend.controller.request.ProductRequest;
import com.airtest.stockbackend.model.Product;

public interface ProductService {
	List<Product> getAllProduct();									//* return ข้อมูล มา
	Product getProductById(Long id);								//* By Id 
	
	Product createProduct(ProductRequest productRequest);			//create product request
	
	Product updateProduct(ProductRequest productRequest,Long id);	//* update product request , Long Id
	
	void deleteProduct(Long id);									//*  Delete Long id ไม่คืนค่า

	Product getProductByName(String name);
	
//	List<Product> getProductByNameAndStock(String name ,int stock);
	
	//*check สิoค้าหมด stock
//	List<Product> getProductOutOfStock();
	
	//* ชื่อ และ ราคา
//	List<Product> getProductByNameAndPrice(String name ,int price);





}					
