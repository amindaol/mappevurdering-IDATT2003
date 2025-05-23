package edu.ntnu.idi.idatt.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

  @Test
  void testRollReturnsValueBetween1And6() {
    Die die = new Die();
    int result = die.roll();
    assertTrue(result >= 1 && result <= 6,
        "Die roll should return a value between 1 and 6");
  }

  @Test
  void testGetValueReturnsLastRoll() {
    Die die = new Die();
    int rolled = die.roll();
    int value = die.getValue();
    assertEquals(rolled, value, "getValue() should return the last rolled value");
  }

  @Test
  void testGetValueThrowsIfNotRolled() {
    Die die = new Die();
    Exception exception = assertThrows(IllegalStateException.class, die::getValue);
    assertEquals("Die has not been rolled yet.", exception.getMessage());
  }

  @Test
  void testRollMultipleTimesChangesValue() {
    Die die = new Die();
    die.roll();
    int first = die.getValue();
    boolean differentFound = false;

    for (int i = 0; i < 10; i++) {
      int newRoll = die.roll();
      if (newRoll != first) {
        differentFound = true;
        break;
      }
    }

    assertTrue(differentFound,
        "Rolling multiple times should eventually give a different value (not guaranteed, but likely)");
  }
}
