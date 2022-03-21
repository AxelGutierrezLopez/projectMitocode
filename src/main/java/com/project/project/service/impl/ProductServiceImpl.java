package com.project.project.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.project.entity.Category;
import com.project.project.entity.Product;
import com.project.project.exception.ResourceNotFoundException;
import com.project.project.payload.ConsolidatedDto;
import com.project.project.payload.ProductDto;
import com.project.project.payload.ProductResponse;
import com.project.project.repository.CategoryRepository;
import com.project.project.repository.ProductRepository;
import com.project.project.payload.CategoryDto;
import com.project.project.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;
	private CategoryServiceImpl categoryServiceImpl;
	private ModelMapper mapper;

	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
			ModelMapper mapper, CategoryServiceImpl categoryServiceImpl) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.categoryServiceImpl = categoryServiceImpl;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public ProductDto createProduct(ProductDto ProductDto) {
		Product product = mapToEntity(ProductDto);
		Product newProduct = productRepository.save(product);
		ProductDto ProductResponse = mapToDTO(newProduct);
		return ProductResponse;
	}

	@Transactional
	@Override
	public ProductResponse getProducts(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Product> products = productRepository.findAll(pageable);
		List<Product> listOfProducts = products.getContent();
		List<ProductDto> content = listOfProducts.stream().map(product -> mapToDTO(product))
				.collect(Collectors.toList());

		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(content);
		productResponse.setPageNo(products.getNumber());
		productResponse.setPageSize(products.getSize());
		productResponse.setTotalElements(products.getTotalElements());
		productResponse.setTotalPages(products.getTotalPages());
		productResponse.setLast(products.isLast());

		return productResponse;
	}

	@Transactional
	@Override
	public ProductDto getProductById(long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		return mapToDTO(product);
	}

	@Transactional
	@Override
	public ProductDto updateProduct(long id, ProductDto productDto) {
		Category category = categoryRepository.findById(productDto.getCategory().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", productDto.getCategory().getId()));

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		product.setName(productDto.getName());
		System.out.print(productDto.getExpiration_date());
		product.setExpiration_date(productDto.getExpiration_date());
		product.setStock(productDto.getStock());
		product.setSale_price(productDto.getSale_price());
		product.setCategory(category);
		// CATEGORY
		Product updateProduct = productRepository.save(product);
		return mapToDTO(updateProduct);
	}
	
	@Transactional
	@Override
	public void deleteProductById(long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		productRepository.delete(product);
	}

	// CONSOLIDADO
	@Transactional
	@Override
	public ConsolidatedDto getProductsByCategoryId(long categoryId) {
		// Search category
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		List<Product> listOfProducts = productRepository.findByCategory(category);
		List<ProductDto> products = listOfProducts.stream().map(product -> mapToDTO(product))
				.collect(Collectors.toList());

		ConsolidatedDto consolidatedDto = new ConsolidatedDto();
		consolidatedDto.setCategory(categoryServiceImpl.mapToDTO(category));
		consolidatedDto.setProducts(products);

		return consolidatedDto;
	}

	// BUSQUEDA POR NOMBRE
	@Transactional
	@Override
	public List<ProductDto> getProductsByName(String name) {

		List<Product> listOfProducts = productRepository.searchByNameLike(name);
		List<ProductDto> products = listOfProducts.stream().map(product -> mapToDTO(product))
				.collect(Collectors.toList());
		return products;
	}

	// BUSQUEDA POR NOMBRE
	@Transactional
	@Override
	public List<ProductDto> getProductsByExpiration() {
		Date dateNow = new Date();
		List<Product> listOfProducts = productRepository.findProductsExpiration(dateNow);
		List<ProductDto> products = listOfProducts.stream().map(product -> mapToDTO(product))
				.collect(Collectors.toList());
		return products;
	}

	private ProductDto mapToDTO(Product product) {
		ProductDto productDto = mapper.map(product, ProductDto.class);
		CategoryDto categoryDto = mapper.map(product.getCategory(), CategoryDto.class);
		// SETTER IN PRODUCTDTO
		productDto.setCategory(categoryDto);
		return productDto;
	}

	private Product mapToEntity(ProductDto productDto) {
		Product product = mapper.map(productDto, Product.class);
		Category category = mapper.map(productDto.getCategory(), Category.class);
		// SETTER IN PRODUCTDTO
		product.setCategory(category);
		return product;
	}

}
