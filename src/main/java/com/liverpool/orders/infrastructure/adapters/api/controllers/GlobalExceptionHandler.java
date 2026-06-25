package com.liverpool.orders.infrastructure.adapters.api.controllers;

import com.liverpool.orders.domain.exceptions.BusinessException;
import com.liverpool.orders.domain.exceptions.CustomerNotFoundException;
import com.liverpool.orders.infrastructure.adapters.api.dto.ApiError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;

import java.time.Instant;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiError> handle(
            CustomerNotFoundException ex) {

        log.error(ex.getMessage());
        log.error(ex.getLocalizedMessage());
        log.error(ex.getCause());

        Arrays.stream(ex.getStackTrace()).forEach(log::error);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "CUSTOMER_NOT_FOUND",
                        ex.getMessage(),
                        Instant.now()));
    }

    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<ApiError> handleWebClient(
            CustomerNotFoundException ex) {

        log.error(ex.getMessage());
        log.error(ex.getLocalizedMessage());
        log.error(ex.getCause());

        Arrays.stream(ex.getStackTrace()).forEach(log::error);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        "SERVER_ERROR",
                        ex.getMessage(),
                        Instant.now()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(
            BusinessException ex) {

        log.error(ex.getMessage());
        log.error(ex.getLocalizedMessage());
        log.error(ex.getCause());

        Arrays.stream(ex.getStackTrace()).forEach(log::error);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        "SERVER_ERROR",
                        ex.getMessage(),
                        Instant.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBadRequest(
            HttpMessageNotReadableException ex) {

        log.error(ex.getMessage());
        log.error(ex.getLocalizedMessage());
        log.error(ex.getCause());

        Arrays.stream(ex.getStackTrace()).forEach(log::error);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        "BAD_REQUEST",
                        ex.getMessage(),
                        Instant.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleBadRequest(
            MethodArgumentNotValidException ex) {

        log.error(ex.getMessage());
        log.error(ex.getLocalizedMessage());
        log.error(ex.getCause());

        Arrays.stream(ex.getStackTrace()).forEach(log::error);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        "BAD_REQUEST",
                        ex.getMessage(),
                        Instant.now()));
    }
}
