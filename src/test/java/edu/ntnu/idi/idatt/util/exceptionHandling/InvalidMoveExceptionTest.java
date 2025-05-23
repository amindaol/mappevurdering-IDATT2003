package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidMoveExceptionTest {

  @Test
  void testInvalidMoveException() {
    Exception exception = assertThrows(InvalidMoveException.class, () -> {
      throw new InvalidMoveException("Invalid move attempted.");
    });

    assertEquals("Invalid move attempted.", exception.getMessage());
  }
}