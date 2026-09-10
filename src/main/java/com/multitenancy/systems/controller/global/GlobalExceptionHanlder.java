package com.multitenancy.systems.controller.global;

import com.multitenancy.systems.exceptions.BadRequestException;
import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.exceptions.ResourceNotFoundException;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHanlder {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHanlder.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<DefaultResponseFormat> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        String errorId = UUID.randomUUID().toString();
        DefaultResponseFormat res = new DefaultResponseFormat();

        logger.error("[Error Id: {}] Failure: {}", errorId, ex.getMessage());

        res.setSuccess(false);
        res.setMessage(ex.getMessage());
        res.setErrorId(errorId);

        return new ResponseEntity<DefaultResponseFormat>(
                res,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<DefaultResponseFormat> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();
        DefaultResponseFormat res = new DefaultResponseFormat();

        logger.error("[Error Id: {}] Failure: {}", errorId, ex.getMessage());

        res.setSuccess(false);
        res.setMessage(ex.getMessage());
        res.setErrorId(errorId);

        return new ResponseEntity<DefaultResponseFormat>(
                res,
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<DefaultResponseFormat> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();
        DefaultResponseFormat res = new DefaultResponseFormat();

        logger.error("[Error Id: {}] Failure: {}", errorId, ex.getMessage());

        res.setSuccess(false);
        res.setMessage(ex.getMessage());
        res.setErrorId(errorId);

        return new ResponseEntity<DefaultResponseFormat>(
                res,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE
        );
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

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<DefaultResponseFormat> missingRequestHeaderHandler(MissingRequestHeaderException exception) {

        DefaultResponseFormat res = new DefaultResponseFormat();

        res.setSuccess(false);
        res.setMessage(exception.getMessage());

        return new ResponseEntity<DefaultResponseFormat>(
                res,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultResponseFormat> badRequestHandler(BadRequestException exception) {

        DefaultResponseFormat res = new DefaultResponseFormat();

        res.setSuccess(false);
        res.setMessage(exception.getMessage());

        return new ResponseEntity<DefaultResponseFormat>(
                res,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponseFormat> generalErrorHandler (Exception exception, HttpServletRequest request) {
        String errorId = UUID.randomUUID().toString();

        logger.error("[Error Id: {}] Failure: {}", errorId, exception.getMessage());

        DefaultResponseFormat res = new DefaultResponseFormat();

        res.setSuccess(true);
        res.setMessage("Internal Server Error");
        res.setErrorId(errorId);

        request.setAttribute("AdviceIDError", errorId);
        request.setAttribute("messageError", exception.getMessage());

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
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
