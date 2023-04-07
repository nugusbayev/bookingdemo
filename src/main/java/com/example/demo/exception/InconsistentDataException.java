package com.example.demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InconsistentDataException extends Exception {

  public InconsistentDataException() {
    super("error.please_contact_admin");
  }
}