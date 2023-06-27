package com.photoapp.apiusers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<String> handleDuplicateEntryException(java.sql.SQLIntegrityConstraintViolationException ex) {
    String errorMessage = "Duplicate entry error: " + ex.getMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
  }

  // 其他異常處理方法...

}
