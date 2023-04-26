package com.happytech.Electrostore.controller;
import com.happytech.Electrostore.dto.ProductDto;

import com.happytech.Electrostore.payloads.ApiResponse;
import com.happytech.Electrostore.payloads.ImageResponse;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.service.FileService;
import com.happytech.Electrostore.service.ProductServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;
    @Autowired
    private ProductServiceI productServiceI;
    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto product = this.productServiceI.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{productId}")
    public  ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long productId){
        ProductDto productDto1 = this.productServiceI.updateProduct(productDto, productId);
        return new ResponseEntity<>(productDto1,HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        this.productServiceI.deleteProduct(productId);
        ApiResponse reponse = ApiResponse.builder().message("Product deleted successfully").success(true).build();
        return new ResponseEntity<>(reponse,HttpStatus.OK);
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto product = this.productServiceI.getSingleProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam (value = "pageNumber",defaultValue = "0",required =false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "productId",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        PageableResponse<ProductDto> allProducts = this.productServiceI.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
    }

    @GetMapping("/getAllLiveProducts")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
            @RequestParam (value = "pageNumber",defaultValue = "0",required =false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "productId",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        PageableResponse<ProductDto> allLiveProduct = this.productServiceI.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allLiveProduct,HttpStatus.OK);
    }

    @GetMapping("/searchKeyword/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchByKeyword(
            @RequestParam (value = "pageNumber",defaultValue = "0",required =false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "productId",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir,
            @PathVariable String subTitle
    ){
        PageableResponse<ProductDto> keyword = this.productServiceI.searchByKeyword(pageNumber, pageSize, sortBy, sortDir, subTitle);
        return new ResponseEntity<>(keyword,HttpStatus.OK);
    }

    //uploadingImage
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable Long productId,
            @RequestParam("productImage") MultipartFile image
            ) throws IOException {

        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productServiceI.getSingleProductById(productId);
        productDto.setProductImageName(fileName);

        ProductDto dto = productServiceI.updateProduct(productDto, productId);
        ImageResponse imeResponse = ImageResponse.builder().imageName(dto.getProductImageName()).message("Image name successfully Added").status(HttpStatus.CREATED).success(true).build();
        return new ResponseEntity<>(imeResponse, HttpStatus.CREATED);
    }

    //serve Image
    @GetMapping("/image/{productId}")
    public void serveImage(@PathVariable Long productId, HttpServletResponse response) throws IOException {
        ProductDto productById = productServiceI.getSingleProductById(productId);
        logger.info("product image Name : {} ",productById.getProductImageName());
        InputStream resource = fileService.getResource(imagePath, productById.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        // Note--> This is the case of serialization in our real Application
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
