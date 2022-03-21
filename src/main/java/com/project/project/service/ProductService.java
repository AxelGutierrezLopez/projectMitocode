package com.project.project.service;

import java.util.List;

import com.project.project.payload.ConsolidatedDto;
import com.project.project.payload.ProductDto;
import com.project.project.payload.ProductResponse;

public interface ProductService {
	ProductDto createProduct(ProductDto ProductDto);

	ProductResponse getProducts(int pageNo, int pageSize, String sortBy, String sortDir);

	ProductDto getProductById(long id);

	ProductDto updateProduct(long id, ProductDto productDto);

	void deleteProductById(long id);

	// CONSOLIDADO
	ConsolidatedDto getProductsByCategoryId(long categoryId);

	// BUSQUEDA POR NOMBRE
	List<ProductDto> getProductsByName(String name);

	// PRODUCTOS VENCIDOS
	List<ProductDto> getProductsByExpiration();
}
