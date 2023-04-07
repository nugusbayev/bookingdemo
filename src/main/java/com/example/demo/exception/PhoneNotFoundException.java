package com.example.demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class PhoneNotFoundException extends Exception {

  public PhoneNotFoundException() {
    super("error.phone.not_found");
  }
}