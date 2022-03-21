package com.project.project.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.project.payload.ConsolidatedDto;
import com.project.project.payload.ProductDto;
import com.project.project.payload.ProductResponse;
import com.project.project.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto ProductDto) {
		return new ResponseEntity<>(productService.createProduct(ProductDto), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("")
	public ProductResponse getProducts(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		return productService.getProducts(pageNo, pageSize, sortBy, sortDir);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
			@PathVariable(name = "id") long id) {
		ProductDto productResponse = productService.updateProduct(id, productDto);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") long id) {
		productService.deleteProductById(id);
		return new ResponseEntity<>("Product entity deleted successfully.", HttpStatus.OK);
	}

	// CONSOLIDADO : CATEGORIA CON TODOS SUS PRODUCTOS
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<ConsolidatedDto> getProductsByProductId(@PathVariable(name = "categoryId") long categoryId) {
		return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
	}

	// BUSQUEDA POR NOMBRE
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/name/{name}")
	public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable(name = "name") String name) {
		return ResponseEntity.ok(productService.getProductsByName(name));
	}
	
	// PRODUCTOS VENCIDOS
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/expiration")
	public ResponseEntity<List<ProductDto>> getProductsByExpiration() {
		return ResponseEntity.ok(productService.getProductsByExpiration());
	}
}
