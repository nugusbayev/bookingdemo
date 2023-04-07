package com.example.demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PhoneAlreadyBookedException extends Exception {

  public PhoneAlreadyBookedException() {
    super("error.phone_already_booked");
  }
}