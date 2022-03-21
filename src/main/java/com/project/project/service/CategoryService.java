package com.project.project.service;

import com.project.project.payload.CategoryDto;
import com.project.project.payload.CategoryResponse;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);

	CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);

	CategoryDto getCategoryById(long id);

	CategoryDto updateCategory(long id, CategoryDto categoryDto);

	void deleteCategoryById(long id);
}
