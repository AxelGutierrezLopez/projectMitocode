package com.project.project.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.project.project.entity.Category;
import com.project.project.entity.Product;

@Repository
public interface ProductRepository extends GenericRepository<Product, Long> {
	List<Product> findByCategory(Category category);

	@Query(value = "SELECT * FROM products WHERE products.name LIKE %:name%", nativeQuery = true)
	List<Product> searchByNameLike(@Param("name") String name);

	@Query(value = "SELECT * FROM products WHERE products.expiration_date <= :expiration_date", nativeQuery = true)
	List<Product> findProductsExpiration(@Param("expiration_date") Date expiration_date);
}
