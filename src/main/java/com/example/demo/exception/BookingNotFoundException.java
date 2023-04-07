package com.example.demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookingNotFoundException extends Exception {

  public BookingNotFoundException() {
    super("error.booking.not_found");
  }
}