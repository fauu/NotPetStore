package com.github.fauu.notpetstore.common.backing;

import com.github.fauu.notpetstore.common.BadRequestException;
import com.github.fauu.notpetstore.common.ResourceNotFoundException;
import com.github.fauu.notpetstore.common.feedback.ExceptionFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
class GlobalExceptionHandlingControllerAdvice {

  private static final Logger LOG
      = LoggerFactory.getLogger(GlobalExceptionHandlingControllerAdvice.class);

  // TODO: Test these handlers?

  @ExceptionHandler({NoHandlerFoundException.class,
                     ResourceNotFoundException.class})
  public @ResponseStatus(HttpStatus.NOT_FOUND) ModelAndView
  notFound(HttpServletResponse response) {
    return prepareResponseAndMav(response,
                                 HttpStatus.NOT_FOUND,
                                 ExceptionFeedback.PAGE_NOT_FOUND_DEFAULT);
  }

  @ExceptionHandler({TypeMismatchException.class,
                     BadRequestException.class})
  public @ResponseStatus(HttpStatus.BAD_REQUEST) ModelAndView
  badRequest(HttpServletResponse response) {
    return prepareResponseAndMav(response,
                                 HttpStatus.BAD_REQUEST,
                                 ExceptionFeedback.BAD_REQUEST_DEFAULT);
  }


  @ExceptionHandler(Throwable.class)
  public @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) ModelAndView
  any(Throwable e, HttpServletResponse response) {
    LOG.error("Returned Internal Server Error", e);

    return prepareResponseAndMav(response,
                                 HttpStatus.INTERNAL_SERVER_ERROR,
                                 ExceptionFeedback.SERVER_ERROR_DEFAULT);
  }

  private ModelAndView prepareResponseAndMav(HttpServletResponse response,
                                             HttpStatus status,
                                             ExceptionFeedback exceptionFeedback) {
    response.setStatus(status.value());

    ModelAndView mav = new ModelAndView();

    mav.addObject("exceptionFeedback", exceptionFeedback);

    mav.setViewName("exception");

    return mav;
  }

}
