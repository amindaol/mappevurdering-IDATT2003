package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.game.Die;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

  private Die die;

  @BeforeEach
  void setUp() {
    die = new Die();
  }

  @Test
  void roll_returnValueOneToSix() {
    int value = die.roll();
    assertTrue(value >= 1 && value <= 6);
  }

  @Test
  void getValue_returnMostRecentValue() {
    int rolledValue = die.roll();
    int valueFromGetValue = die.getValue();
    assertEquals(valueFromGetValue, rolledValue);

  }

  @Test
  void getValue_shouldThrowExceptionIfNotRolled() {
    assertThrows(IllegalStateException.class, () -> die.getValue());
  }

  @Test
  void roll_multipleRollsInRange() {
    for (int i = 0; i < 100; i++) {
      int rolledValue = die.roll();
      assertTrue(rolledValue >= 1 && rolledValue <= 6);
    }
  }

}
