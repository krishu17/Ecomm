package com.happytech.Electrostore.service.ServiceImpl;

import com.happytech.Electrostore.config.AppConstants;
import com.happytech.Electrostore.dto.ProductDto;
import com.happytech.Electrostore.entity.BaseEntity;
import com.happytech.Electrostore.entity.Product;
import com.happytech.Electrostore.entity.User;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.repo.ProductRepo;
import com.happytech.Electrostore.service.ProductServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@SpringBootTest
class ProductServiceImplTest extends BaseEntity {

    @MockBean
    private ProductRepo productRepo;

    @Autowired
    @InjectMocks
    private ProductServiceImpl productServiceI;

    @Autowired
    private ModelMapper mapper;

    Product product;

    Product product1;

    ProductDto productDto;

    @BeforeEach
    public void init(){

        product = Product.builder()
                .brand("Toshiba")
                .price(123.00)
                .description("Product is available")
                .title("AC")
                .quantity(150)
                .rating(5.0)
                .build();

        product1 = Product.builder()
                .brand("SAMSUNG")
                .price(178.00)
                .description("Product is available")
                .title("AC")
                .quantity(170)
                .rating(8.0)
                .build();

        productDto = ProductDto.builder()
                .brand("Havels")
                .price(178.00)
                .description("Product is available")
                .title("Fan")
                .quantity(170)
                .rating(8.0)
                .build();
    }

    @Test
    public void createProductTest() {

        //Arrange
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);
        //Act
        ProductDto productDto = productServiceI.createProduct(this.mapper.map(product, ProductDto.class));
        //Assert
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getTitle(),productDto.getTitle());
    }

    @Test
    public void updateProductTest() {
        Long productId=12L;
        //Arrange
        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);

        //Act
        ProductDto updateProduct = productServiceI.updateProduct(productDto, productId);
        //Assert
        Assertions.assertEquals(productDto.getTitle(), updateProduct.getTitle());
    }

    @Test
    public void deleteProductTest() {
        Long productId=10L;


        //Arrange
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        //Act
        productServiceI.deleteProduct(productId);
        //Assert
        verify(productRepo).findById(10L);

        // Verify that the repository method was called to delete the product
//        verify(productRepo).delete(product);

    }

    @Test
    public void getSingleProductByIdTest() {
        Long productId = 12L;
        //Arrange
        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        //Act
        ProductDto byId = productServiceI.getSingleProductById(productId);
        //Assert
        Assertions.assertNotNull(byId);
    }

    @Test
    public void getAllProductsTest() {

        List<Product> products = List.of(product,product1);
        Page<Product> page = new PageImpl<>(products);
        //Arrange
        Mockito.when(productRepo.findAll((Pageable)Mockito.any())).thenReturn(page);
        //Act
        PageableResponse<ProductDto> allProducts = productServiceI.getAllProducts(1, 2, "title", "asc");
        //Assert
        Assertions.assertEquals(2, allProducts.getContent().size());
    }

    @Test
    public void getAllLiveProductTest() {
        List<Product> products = List.of(product,product1);
        Page<Product> page = new PageImpl<>(products);
        //Arrange
        Mockito.when(productRepo.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);

        //Act
        PageableResponse<ProductDto> allLiveProduct = productServiceI.getAllLiveProduct(1, 2, "title", "desc");

        //Assert
        Assertions.assertEquals(2, allLiveProduct.getContent().size());
    }

    @Test
    public void searchByKeywordTest() {
        String subTitle="AC";

        List<Product> products = List.of(product,product1);
        Page<Product> page = new PageImpl<>(products);

        //Arrange
       Mockito.when(productRepo.findByTitleContaining((Pageable) Mockito.any(),subTitle)).thenReturn(page);
        //Act
        PageableResponse<ProductDto> keyword = productServiceI.searchByKeyword(0, 2, "title", "asc", subTitle);

        //Assert

        Assertions.assertEquals("AC", keyword.getContent().get(0).getTitle());
        Assertions.assertEquals("AC", keyword.getContent().get(1).getTitle());
    }
}