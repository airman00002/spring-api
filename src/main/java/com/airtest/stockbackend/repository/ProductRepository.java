package com.airtest.stockbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.airtest.stockbackend.model.Product;

//*DAO data access object
public interface ProductRepository extends JpaRepository<Product, Long> { // *1 class mapping database 2 primary key

	// * SELECT * FROM Product WHERE name = "etc" หรือ limit 1 fingTopByName
	Optional<Product> findTopByName(String name);

	// * SELECT * FROM Product WHERE name LIKE = "%etc%" AND stock > x order by
	// stock decs
	List<Product> findByNameContainingAndStockGreaterThanOrderByStockDesc(String name, int stock); // * ค้นหาชื่อ
																									// ที่อนู่ระหว่าง
																									// และ หา stock
																									// ที่มากกว่า ......

	// *JPQL เอา java มา query จะไม่มี * แต่ ถ้าจะใช้ แบบ SQL....
	@Query(value = "SELECT * FROM Product WHERE STOCK = 0 ", nativeQuery = true) // * sql command
	List<Product> checkOutOfStock();
	
	// *  WHERE LIKE  ตัวแปร name และราคา
	@Query(value = "SELECT * FROM Product WHERE name LIKE %:product_name% AND price > :price  ", nativeQuery = true) // * sql command
	List<Product> searchNameAndPrice(@Param("product_name") String name,int price);
	
}
