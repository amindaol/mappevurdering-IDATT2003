package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerNotFoundExceptionTest {

  @Test
  void testMessageContainsPlayerName() {
    String name = "Aminda";
    PlayerNotFoundException exception = new PlayerNotFoundException(name);

    assertTrue(exception.getMessage().contains(name));
    assertEquals("player not found: Aminda", exception.getMessage());
  }
}
