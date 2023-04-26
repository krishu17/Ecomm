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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Profile(value = {"dev","qa","prod","pilot"})
public class ProductServiceImpl implements ProductServiceI {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;

    private  static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating productDto...");
        Product product = modelMapper.map(productDto, Product.class);

        //adding date
        product.setAddedDate(new Date());

        Product save = this.productRepo.save(product);
        ProductDto map = this.modelMapper.map(save, ProductDto.class);
        log.info("product created successfully !!");
        return map;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        log.info("updating productDto...");
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
        save.setProductImageName(productDto.getProductImageName());
        Product save1 = this.productRepo.save(save);

        ProductDto map = this.modelMapper.map(save1, ProductDto.class);
        log.info("product updated successfully !!");
        return map;
    }

    @Override
    public void deleteProduct(Long productId) {
        log.info("finding product ...");
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT, AppConstants.PRODUCT_ID, productId));
        this.productRepo.delete(product);
        log.info("product deleted successfully !!");
    }

    @Override
    public ProductDto getSingleProductById(Long productId) {
        log.info("finding product ...");
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT, AppConstants.PRODUCT_ID, productId));
        ProductDto map = this.modelMapper.map(product, ProductDto.class);
        log.info("product got successfully !!");
        return map;
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.DESC)?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        log.info("getting all products...");
        Page<Product> page = this.productRepo.findAll(pageable);

        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        log.info("product got successfully in pagination !!");
        return response;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.DESC)?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        log.info("getting all Live products...");
        Page<Product> page = this.productRepo.findByLiveTrue(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        log.info("product got successfully in pagination !!");
        return response;
    }

    @Override
    public PageableResponse<ProductDto> searchByKeyword(int pageNumber, int pageSize, String sortBy, String sortDir,String subTitle) {

        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.DESC)?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        log.info("searching products...");
        Page<Product> titleContaining = this.productRepo.findByTitleContaining(pageable, subTitle);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(titleContaining, ProductDto.class);
        log.info("product got successfully in pagination !!");
        return response;
    }
}
