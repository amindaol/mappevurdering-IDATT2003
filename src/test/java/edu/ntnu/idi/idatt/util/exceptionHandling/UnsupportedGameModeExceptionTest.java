package edu.ntnu.idi.idatt.util.exceptionHandling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnsupportedGameModeExceptionTest {

  @Test
  void testUnsupportedGameModeException() {
    Exception exception = assertThrows(UnsupportedGameModeException.class, () -> {
      throw new UnsupportedGameModeException("Game mode is not supported.");
    });

    assertEquals("Game mode is not supported.", exception.getMessage());
  }
}