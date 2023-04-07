package com.example.demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PhoneAlreadyReturnedException extends Exception {

  public PhoneAlreadyReturnedException() {
    super("error.phone_already_returned");
  }
}