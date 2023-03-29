package com.happytech.Electrostore.dto;

import com.happytech.Electrostore.validate.ImageNameValidate;
import lombok.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long categoryId;
    @NotBlank
    @Size(max = 20  , min = 4)
    private String title;
    @Size(max = 1000)
    @NotBlank(message = "Tell me something about yourself")
    private String description;
    @ImageNameValidate
    private String coverImage;

}
