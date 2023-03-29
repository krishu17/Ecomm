package com.happytech.Electrostore.controller;

import com.happytech.Electrostore.dto.CategoryDto;
import com.happytech.Electrostore.service.CategoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceI categoryServiceI;

    //createCAtegory
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){

        CategoryDto category = this.categoryServiceI.createCategory(categoryDto);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/getCategoryById/{getCategory}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("getCategory") Long categoryId ){

        CategoryDto singleCategory = this.categoryServiceI.getSingleCategory(categoryId);

        return new ResponseEntity<>(singleCategory,HttpStatus.OK);
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId){

        CategoryDto categoryDto1 = this.categoryServiceI.updateCategory(categoryDto, categoryId);

        return new ResponseEntity<>(categoryDto1,HttpStatus.CREATED);
    }
}
