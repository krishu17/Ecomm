package com.happytech.Electrostore.service;

import com.happytech.Electrostore.Helper.Helper;
import com.happytech.Electrostore.config.AppConstants;
import com.happytech.Electrostore.dto.CategoryDto;
import com.happytech.Electrostore.entity.Category;
import com.happytech.Electrostore.entity.User;
import com.happytech.Electrostore.exceptions.ResourceNotFoundException;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.repo.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryServiceI{
    @Autowired
    private CategoryRepo catRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category cat = this.catRepo.save(category);
        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public CategoryDto getSingleCategory(Long categoryId) {

//        Category map = this.modelMapper.map(categoryId, Category.class);
        Category category = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,sort);

        Page<Category> page = catRepo.findAll(pageRequest);

        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);

        return response;
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category category = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
        this.catRepo.delete(category);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        Category map = this.modelMapper.map(categoryDto, Category.class);
        Category category = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
        category.setCategoryId(categoryDto.getCategoryId());
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());

        return this.modelMapper.map(category,CategoryDto.class);
    }
}
