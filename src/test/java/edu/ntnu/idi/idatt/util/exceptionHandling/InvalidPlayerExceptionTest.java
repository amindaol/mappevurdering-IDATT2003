package edu.ntnu.idi.idatt.util.exceptionHandling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidPlayerExceptionTest {

  @Test
  void testInvalidPlayerException() {
    Exception exception = assertThrows(InvalidPlayerException.class, () -> {
      throw new InvalidPlayerException("Player is invalid.");
    });

    assertEquals("Player is invalid.", exception.getMessage());
  }
}
