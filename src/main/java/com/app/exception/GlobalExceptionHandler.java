package com.app.exception;

import com.app.dto.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = PlaceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleException(PlaceNotFoundException ex){
        log.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiResponse(null, ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

}
