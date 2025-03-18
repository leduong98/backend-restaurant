package com.be.restaurant.domain.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalHandleException {

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<ExceptionResponse>> globalExceptionHandler(Exception ex, ServerWebExchange exchange) {
    log.error(ex.getMessage());
    if (ex instanceof BusinessException businessException) {
      ExceptionResponse exceptionResponse = new ExceptionResponse(
              businessException.status,
              new Date(),
              businessException.getMessage(),
              ex.getMessage(),
              exchange.getRequest().getPath().value()
      );
      return Mono.just(ResponseEntity.status(businessException.status).body(exceptionResponse));
    }

    ExceptionResponse exceptionResponse = new ExceptionResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            new Date(),
            "Đã có lỗi xảy ra.",
            ex.getMessage(),
            exchange.getRequest().getPath().value()
    );
    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Mono<ResponseEntity<ValidDetails>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, ServerWebExchange exchange) {
    ValidDetails validDetails = new ValidDetails();
    Map<String, String> message = new HashMap<>();
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    for (FieldError fieldError : fieldErrors) {
      message.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    validDetails.setMessage(message);
    validDetails.setStatus(HttpStatus.BAD_REQUEST.value());
    validDetails.setTimestamp(new Date());
    validDetails.setError("Not valid exception");
    validDetails.setPath(exchange.getRequest().getPath().value());

    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validDetails));
  }

  @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
  public Mono<ResponseEntity<ExceptionResponse>> notFoundException(ChangeSetPersister.NotFoundException ex, ServerWebExchange exchange) {
    ExceptionResponse errorDetails = new ExceptionResponse(
            HttpStatus.NOT_FOUND,
            new Date(),
            ex.getMessage(),
            ex.getMessage(),
            exchange.getRequest().getPath().value()
    );
    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails));
  }
}