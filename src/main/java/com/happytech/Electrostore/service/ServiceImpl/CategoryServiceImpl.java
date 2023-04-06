package com.happytech.Electrostore.service.ServiceImpl;

import com.happytech.Electrostore.Helper.Helper;
import com.happytech.Electrostore.config.AppConstants;
import com.happytech.Electrostore.dto.CategoryDto;
import com.happytech.Electrostore.entity.Category;
import com.happytech.Electrostore.entity.User;
import com.happytech.Electrostore.exceptions.ResourceNotFoundException;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.repo.CategoryRepo;
import com.happytech.Electrostore.service.CategoryServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile(value = {"dev","qa","prod","pilot"})
public class CategoryServiceImpl implements CategoryServiceI {
    @Autowired
    private CategoryRepo catRepo;
    @Autowired
    private ModelMapper modelMapper;

    private Logger log= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("creating category ...");
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category cat = this.catRepo.save(category);
        CategoryDto dto = this.modelMapper.map(cat, CategoryDto.class);
        log.info("category created successfully...");
        return dto;
    }

    @Override
    public CategoryDto getSingleCategory(Long categoryId) {
        log.info("getting by category ...");
//        Category map = this.modelMapper.map(categoryId, Category.class);
        Category category = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

        CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
        log.info("category got with id ... "+categoryId);
        return dto;
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,sort);
        log.info("getting all the categories along with pagination...");

        Page<Category> page = catRepo.findAll(pageRequest);

        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);

        log.info("got all the categories along with pagination...");
        return response;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        log.info("finding with category id to dlete...");
        Category category = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

        this.catRepo.delete(category);
        log.info("category deleted successfully !!");
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        log.info("updating category initialized .....");

        Category map = this.modelMapper.map(categoryDto, Category.class);
        Category category = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
        category.setCategoryId(categoryDto.getCategoryId());
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        Category save = this.catRepo.save(category);
        CategoryDto dto = this.modelMapper.map(save, CategoryDto.class);

        log.info("category Upadated successfully...");
        return dto;
    }
}
