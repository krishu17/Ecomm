package com.happytech.Electrostore.service;

import com.happytech.Electrostore.dto.CategoryDto;
import com.happytech.Electrostore.entity.Category;
import com.happytech.Electrostore.payloads.PageableResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryServiceI{

    //createCATEGORY
    CategoryDto createCategory(CategoryDto categoryDto);

    //updateCategory
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    //getCategory
    CategoryDto getSingleCategory(Long categoryId);

    //getAllCAtegory
    PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);

    //deleteCAetegory
    void deleteCategory(Long categoryId);



}
