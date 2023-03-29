package com.happytech.Electrostore.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValidate,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //logic

        if(value.isBlank()){
            return false;
        }else{
            return true;
        }

//        return false;
    }
}
