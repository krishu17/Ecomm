package com.happytech.Electrostore.service.ServiceImpl;

import com.happytech.Electrostore.Helper.Helper;
import com.happytech.Electrostore.config.AppConstants;
import com.happytech.Electrostore.dto.ProductDto;
import com.happytech.Electrostore.entity.Product;
import com.happytech.Electrostore.exceptions.ResourceNotFoundException;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.repo.ProductRepo;
import com.happytech.Electrostore.service.ProductServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductServiceI {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto createProduct(ProductDto productDto) {

        Product product = modelMapper.map(productDto, Product.class);
        Product save = this.productRepo.save(product);
        return this.modelMapper.map(save, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {

        Product product = this.modelMapper.map(productId, Product.class);

        Product save = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT, AppConstants.PRODUCT_ID, productId));
        save.setBrand(productDto.getBrand());
        save.setDescription(productDto.getDescription());
        save.setPrice(productDto.getPrice());
        save.setRating(productDto.getRating());
        save.setQuantity(productDto.getQuantity());
        save.setTitle(productDto.getTitle());
        save.setLive(productDto.isLive());
        save.setStock(productDto.isStock());
        Product save1 = this.productRepo.save(save);
        return this.modelMapper.map(save1, ProductDto.class);
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT, AppConstants.PRODUCT_ID, productId));
        this.productRepo.delete(product);

    }

    @Override
    public ProductDto getSingleProductById(Long productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT, AppConstants.PRODUCT_ID, productId));
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.DESC)?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepo.findAll(pageable);

        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.DESC)?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepo.findByLiveTrue(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> searchByKeyword(int pageNumber, int pageSize, String sortBy, String sortDir,String subTitle) {

        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.DESC)?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> titleContaining = this.productRepo.findByTitleContaining(pageable, subTitle);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(titleContaining, ProductDto.class);

        return response;
    }
}
