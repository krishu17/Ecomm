package com.happytech.Electrostore.repo;

import com.happytech.Electrostore.dto.ProductDto;
import com.happytech.Electrostore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {

    Page<Product> findByTitleContaining(Pageable pageable ,String subTitle);
    Page<Product> findByLiveTrue(Pageable pageable);

}
