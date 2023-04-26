package com.happytech.Electrostore.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long productId;
    @NotBlank
    @Length(min = 5,max = 25)
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Double price;
    private Integer quantity;
    private boolean stock;
    private Date addedDate;
    private String brand;

    @NotNull
    private boolean live;
    private double rating;

    private String productImageName;

//    private List<String> reviews;
//    private List<String> features;
//    private List<String> tags;
}
