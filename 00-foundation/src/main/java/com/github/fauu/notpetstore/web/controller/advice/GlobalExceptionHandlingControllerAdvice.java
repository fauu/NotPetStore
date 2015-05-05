package com.github.fauu.notpetstore.web.controller.advice;

import com.github.fauu.notpetstore.web.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

  private static final Logger LOG
      = LoggerFactory.getLogger(GlobalExceptionHandlingControllerAdvice.class);

  // TODO: Test these handlers?

  @ExceptionHandler({NoHandlerFoundException.class,
                     ResourceNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String notFound() {
    return "error/404";
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String any(Throwable e) {
    LOG.error("Returned Internal Server Error", e);

    return "error/500";
  }

}
