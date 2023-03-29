package com.happytech.Electrostore.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValueNotFoundException extends RuntimeException{

    String resourceName;
    String fieldName;
    String valueName;

    public ValueNotFoundException(String resourceName, String fieldName, String valueName) {
        super(String.format("%s user is not found with %s : %s",resourceName,fieldName,valueName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.valueName = valueName;
    }
}
