package com.example.demo.common;

import lombok.Data;

@Data
public class ErrorInfo {

  private String url;
  private String message;

  public ErrorInfo(String url, String message) {
    this.url = url;
    this.message = message;
  }
}
