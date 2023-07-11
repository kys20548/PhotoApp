package com.photoapp.apiusers.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

  @ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<String> handleDuplicateEntryException(java.sql.SQLIntegrityConstraintViolationException ex) {
    log.error(ex.getMessage());
    String errorMessage = "Duplicate entry error: " + ex.getMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
  }

  // 其他異常處理方法...

}
