package com.project.project.service.impl;

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
import com.project.project.payload.CategoryDto;
import com.project.project.payload.CategoryResponse;
import com.project.project.repository.CategoryRepository;
import com.project.project.service.CategoryService;
import com.project.project.exception.ResourceNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private ModelMapper mapper;

	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = mapToEntity(categoryDto);
		Category newCategory = categoryRepository.save(category);
		CategoryDto categoryResponse = mapToDTO(newCategory);
		return categoryResponse;
	}

	@Transactional(readOnly = true)
	@Override
	public CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Category> categories = categoryRepository.findAll(pageable);
		List<Category> listOfCategories = categories.getContent();
		List<CategoryDto> content = listOfCategories.stream().map(category -> mapToDTO(category))
				.collect(Collectors.toList());

		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(content);
		categoryResponse.setPageNo(categories.getNumber());
		categoryResponse.setPageSize(categories.getSize());
		categoryResponse.setTotalElements(categories.getTotalElements());
		categoryResponse.setTotalPages(categories.getTotalPages());
		categoryResponse.setLast(categories.isLast());

		return categoryResponse;
	}

	@Transactional(readOnly = true)
	@Override
	public CategoryDto getCategoryById(long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		return mapToDTO(category);
	}

	@Transactional
	@Override
	public CategoryDto updateCategory(long id, CategoryDto categoryDto) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		category.setName(categoryDto.getName());
		category.setDetail(categoryDto.getDetail());
		Category updateCategory = categoryRepository.save(category);
		return mapToDTO(updateCategory);
	}

	@Transactional
	@Override
	public void deleteCategoryById(long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		categoryRepository.delete(category);

	}

	public CategoryDto mapToDTO(Category category) {
		CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
		return categoryDto;
	}

	private Category mapToEntity(CategoryDto categoryDto) {
		Category category = mapper.map(categoryDto, Category.class);
		return category;
	}

}
