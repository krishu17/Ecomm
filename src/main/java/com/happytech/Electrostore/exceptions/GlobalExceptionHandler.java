package com.happytech.Electrostore.exceptions;

import com.happytech.Electrostore.payloads.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){

        logger.info("resource Not Found Exception !!");
        String message = ex.getMessage();

//        ApiResponse apiResponse = new ApiResponse(message, false);
        ApiResponse apiResponse = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

}

@ExceptionHandler(ValueNotFoundException.class)
public ResponseEntity<ApiResponse> valueNotFoundException(ValueNotFoundException vl){

    String message = vl.getMessage();

//    ApiResponse apiResponse = new ApiResponse(message,false);
    ApiResponse apiResponse = ApiResponse.builder().message(vl.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();

    return  new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
}

 @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        Map<String, Object> response = new HashMap<>();

        allErrors.stream().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, message);

        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponse> badApiRequestExceptionHandler(BadApiRequest ex){

    logger.info("Bad Api Request");

        String message = ex.getMessage();

//        ApiResponse apiResponse = new ApiResponse(message, false);

        ApiResponse apiResponse = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

    }
}
