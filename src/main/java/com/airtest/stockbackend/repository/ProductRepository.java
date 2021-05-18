package com.airtest.stockbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airtest.stockbackend.model.Product;

//*DAO data access object
public interface ProductRepository extends JpaRepository<Product, Long> { // *1 class mapping database 2 primary key

	// * SELECT * FROM Product WHERE name = "etc" หรือ limit 1 fingTopByName
	Optional<Product> findTopByName(String name);

}
