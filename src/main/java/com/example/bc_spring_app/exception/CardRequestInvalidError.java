package com.example.bc_spring_app.exception;

public class CardRequestInvalidError extends RuntimeException {
  public CardRequestInvalidError(String message) {
    super(message);
  }
}
