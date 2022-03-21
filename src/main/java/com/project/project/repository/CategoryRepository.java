package com.project.project.repository;

import org.springframework.stereotype.Repository;

import com.project.project.entity.Category;

@Repository
public interface CategoryRepository extends GenericRepository<Category, Long> {

}
