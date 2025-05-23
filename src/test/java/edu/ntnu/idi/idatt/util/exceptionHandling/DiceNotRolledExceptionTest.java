package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceNotRolledExceptionTest {

  @Test
  void testDiceNotRolledException() {
    Exception exception = assertThrows(DiceNotRolledException.class, () -> {
      throw new DiceNotRolledException("Dice not rolled.");
    });

    assertEquals("Dice not rolled.", exception.getMessage());
  }
}