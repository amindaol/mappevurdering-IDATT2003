package edu.ntnu.idi.idatt.util.exceptionHandling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerNotOnBoardExceptionTest {

  @Test
  void testPlayerNotOnBoardException() {
    Exception exception = assertThrows(PlayerNotOnBoardException.class, () -> {
      throw new PlayerNotOnBoardException("Player not on the board.");
    });

    assertEquals("Player not on the board.", exception.getMessage());
  }
}