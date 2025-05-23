package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerNotConfiguredExceptionTest {

  @Test
  void testPlayerNotConfiguredException() {
    Exception exception = assertThrows(PlayerNotConfiguredException.class, () -> {
      throw new PlayerNotConfiguredException("Player not configured.");
    });

    assertEquals("Player not configured.", exception.getMessage());
  }
}