package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidLadderExceptionTest {

  @Test
  void testInvalidLadderException() {
    Exception exception = assertThrows(InvalidLadderException.class, () -> {
      throw new InvalidLadderException("Invalid ladder configuration.");
    });

    assertEquals("Invalid ladder configuration.", exception.getMessage());
  }
}