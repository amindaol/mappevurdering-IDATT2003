package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFileNotFoundExceptionTest {

  @Test
  void testPlayerFileNotFoundException() {
    Exception exception = assertThrows(PlayerFileNotFoundException.class, () -> {
      throw new PlayerFileNotFoundException("Player file not found.");
    });

    assertEquals("Player file not found.", exception.getMessage());
  }
}