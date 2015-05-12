package com.github.fauu.notpetstore.web.controller.advice;

import com.github.fauu.notpetstore.web.exception.ResourceNotFoundException;
import com.github.fauu.notpetstore.web.feedback.ExceptionFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

  private static final Logger LOG
      = LoggerFactory.getLogger(GlobalExceptionHandlingControllerAdvice.class);

  // TODO: Test these handlers?

  @ExceptionHandler({NoHandlerFoundException.class,
                     ResourceNotFoundException.class})
  public @ResponseStatus(HttpStatus.NOT_FOUND) ModelAndView notFound() {
    ModelAndView mav = new ModelAndView();

    mav.addObject("exceptionFeedback", ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT);

    mav.setViewName("exception");

    return mav;
  }

  @ExceptionHandler(Throwable.class)
  public @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) ModelAndView any(Throwable e) {
    LOG.error("Returned Internal Server Error", e);

    ModelAndView mav = new ModelAndView();

    mav.addObject("exceptionFeedback", ExceptionFeedback.SERVER_ERROR_DEFAULT);

    mav.setViewName("exception");

    return mav;
  }

}
