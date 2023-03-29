package com.happytech.Electrostore.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

// custom validation
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface  ImageNameValidate {

    //represent error message
    String message() default "Invalid image Name !";


    // represent group of constraints
    Class<?>[] groups() default { };

    // additional information about annotations
    Class<? extends Payload>[] payload() default { };

}
