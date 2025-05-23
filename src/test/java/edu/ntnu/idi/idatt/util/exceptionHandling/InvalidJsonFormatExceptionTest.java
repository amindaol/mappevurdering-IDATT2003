package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidJsonFormatExceptionTest {

  @Test
  void testConstructorSetsMessageAndCause() {
    String message = "Invalid JSON structure";
    Throwable cause = new RuntimeException("Unexpected token");

    InvalidJsonFormatException exception = new InvalidJsonFormatException(message, cause);

    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}
