package com.be.restaurant.domain.exceptions;

public enum ErrorMessage {
    WORD_NOT_FOUND("word not found"),
    ;


  public final String val;

  private ErrorMessage(String label) {
    val = label;
  }
}
