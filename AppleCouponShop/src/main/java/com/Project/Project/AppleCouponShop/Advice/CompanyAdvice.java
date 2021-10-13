package com.Project.Project.AppleCouponShop.Advice;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CompanyAdvice {

    @ExceptionHandler(value = {MajorException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception error){
        return new ErrorDetail("there was a problem with admin service", error.getMessage());
    }
}
