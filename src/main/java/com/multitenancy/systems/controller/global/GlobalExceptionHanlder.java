package com.multitenancy.systems.controller.global;

import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.exceptions.ResourceNotFoundException;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHanlder {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHanlder.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponseFormat> generalErrorHandler (Exception exception) {
        String errorId = UUID.randomUUID().toString();

        logger.error("[Error Id: {}] Failure: {}", errorId, exception.getMessage());

        DefaultResponseFormat res = new DefaultResponseFormat();

        res.setSuccess(true);
        res.setMessage("Internal Server Error");
        res.setErrorId(errorId);

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultResponseFormat> resourceNotFoundHandler(ResourceNotFoundException exception) {
        DefaultResponseFormat res = new DefaultResponseFormat();

        res.setSuccess(false);
        res.setMessage(exception.getMessage());

        return new ResponseEntity<DefaultResponseFormat>(
                res,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<DefaultResponseFormat> dataNotFoundHandler (DataNotFoundException exception) {
        DefaultResponseFormat res = new DefaultResponseFormat();

        res.setSuccess(true);
        res.setMessage(exception.getMessage());

        return new ResponseEntity<DefaultResponseFormat>(
                res,
                HttpStatus.OK
        );
    }

}
