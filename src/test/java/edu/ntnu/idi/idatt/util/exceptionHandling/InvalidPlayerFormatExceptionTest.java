package edu.ntnu.idi.idatt.util.exceptionHandling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidPlayerFormatExceptionTest {

  @Test
  void testInvalidPlayerFormatException() {
    Exception exception = assertThrows(InvalidPlayerFormatException.class, () -> {
      throw new InvalidPlayerFormatException("Invalid player format.");
    });

    assertEquals("Invalid player format.", exception.getMessage());
  }
}