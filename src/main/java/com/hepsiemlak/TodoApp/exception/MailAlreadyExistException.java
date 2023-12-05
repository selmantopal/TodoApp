package com.hepsiemlak.TodoApp.exception;

public class MailAlreadyExistException extends RuntimeException {
  public MailAlreadyExistException(String message) {
    super(message);
  }
}