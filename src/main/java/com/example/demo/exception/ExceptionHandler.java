package com.example.demo.exception;

import com.example.demo.common.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @org.springframework.web.bind.annotation.ExceptionHandler(PhoneNotFoundException.class)
  public ErrorInfo handlePhoneNotFoundException(HttpServletRequest req, PhoneNotFoundException ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex.getMessage());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @org.springframework.web.bind.annotation.ExceptionHandler(BookingNotFoundException.class)
  public ErrorInfo handleBookingNotFoundException(HttpServletRequest req, BookingNotFoundException ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex.getMessage());
  }
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @org.springframework.web.bind.annotation.ExceptionHandler(InconsistentDataException.class)
  public ErrorInfo handleInconsistentDataException(HttpServletRequest req, InconsistentDataException ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex.getMessage());
  }
  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  @org.springframework.web.bind.annotation.ExceptionHandler(PhoneAlreadyBookedException.class)
  public ErrorInfo handlePhoneAlreadyBookedException(HttpServletRequest req, PhoneAlreadyBookedException ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex.getMessage());
  }
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @org.springframework.web.bind.annotation.ExceptionHandler(PhoneAlreadyReturnedException.class)
  public ErrorInfo handlePhoneAlreadyReturnedException(HttpServletRequest req, PhoneAlreadyReturnedException ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex.getMessage());
  }
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @org.springframework.web.bind.annotation.ExceptionHandler(IllegalAccessException.class)
  public ErrorInfo handlePhoneAlreadyReturnedException(HttpServletRequest req, IllegalAccessException ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex.getMessage());
  }
}
