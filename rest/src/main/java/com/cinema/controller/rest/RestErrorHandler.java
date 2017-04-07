package com.cinema.controller.rest;

import com.cinema.aop.annotation.Loggable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ControllerAdvice
public class RestErrorHandler {

    @Loggable
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleDataAccesssException(DataAccessException ex){
        return "DataAccessException: " + ex.getLocalizedMessage();
    }

    @Loggable
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException ex){
        return "IllegalArgumentException: " + ex.getLocalizedMessage();
    }

    @Loggable
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleIllegalStateException(IllegalStateException ex){
        return "IllegalStateException: " + ex.getLocalizedMessage();
    }

}
