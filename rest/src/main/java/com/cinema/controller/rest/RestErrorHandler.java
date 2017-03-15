package com.cinema.controller.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER= LogManager.getLogger();

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleDataAccesssException(DataAccessException ex){
        LOGGER.debug("Handling DataAccessExceptiom: ", ex);

        return "DataAccessException: " + ex.getLocalizedMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException ex){
        LOGGER.debug("Handling IllegalArgumentException: ", ex);

        return "IllegalArgumentException: " + ex.getLocalizedMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleIllegalStateException(IllegalStateException ex){
        LOGGER.debug("Handling IllegalStateException: ", ex);

        return "IllegalStateException: " + ex.getLocalizedMessage();
    }
}