package com.happytech.Electrostore.service;

import com.happytech.Electrostore.dto.ProductDto;
import com.happytech.Electrostore.payloads.PageableResponse;

import java.util.List;

public interface ProductServiceI {

    //create
    ProductDto createProduct(ProductDto productDto);
    //update
    ProductDto updateProduct(ProductDto productDto, Long productId);
    //delete
    void deleteProduct(Long productId);
    //get
    ProductDto getSingleProductById(Long productId);
    //getAll
    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);
    //getAll live
    PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
    //search keyword
    PageableResponse<ProductDto> searchByKeyword(int pageNumber, int pageSize, String sortBy, String sortDir,String subTitle);
    //otherMtds

}
