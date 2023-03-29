package com.happytech.Electrostore.dto;

import com.happytech.Electrostore.validate.ImageNameValidate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseEntityDto {


    private Long userId;
    @Size(min=3,max=20,message = "Invalid name !!")
    private String name;
    @Size(min=4,max=6,message="Invalid Gender")
    private String gender;
    @NotBlank(message="email is compulsory")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$",message = "Invalid Email !!")
    private String emailId;

    @NotBlank(message = "password is required !!")
    private String password;
    @NotBlank(message = "Write Something about yourself !!")
    private String about;

    @ImageNameValidate
    private String imageName;
}
