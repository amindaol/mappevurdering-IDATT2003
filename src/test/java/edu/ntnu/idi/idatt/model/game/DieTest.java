package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.game.Die;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link Die}.
 * <p>These tests cover the core behavior of the Die class, including rolling, retrieving values
 * and handling error cases.</p>
 */
class DieTest {

  private Die die;

  @BeforeEach
  void setUp() {
    die = new Die();
  }

  @Test
  void roll_shouldReturnValueOneToSix() {
    int value = die.roll();
    assertTrue(value >= 1 && value <= 6, "Rolled value out of range" + value);
  }

  @Test
  void getValue_shouldReturnMostRecentValue() {
    int rolledValue = die.roll();
    int valueFromGetValue = die.getValue();
    assertEquals(valueFromGetValue, rolledValue, "getValue didn't return the most recent "
        + "value");

  }

  @Test
  void getValue_shouldThrowExceptionIfNotRolled() {
    assertThrows(IllegalStateException.class, () -> die.getValue(), "Expected "
        + "IllegalStateException when getValue() is called before a roll");
  }

  @Test
  void roll_multipleRollsInRange() {
    for (int i = 0; i < 100; i++) {
      int rolledValue = die.roll();
      assertTrue(rolledValue >= 1 && rolledValue <= 6, "Roll #" + i + " produced "
          + "out of range value: " + rolledValue);
    }
  }

  @Test
  void valueUpdatesOnRoll() {
    die.roll();
    int firstRoll = die.getValue();

    die.roll();
    int secondRoll = die.getValue();

    assertTrue(firstRoll >= 1 && firstRoll <= 6, "First roll out of range: "
        + firstRoll);
    assertTrue(secondRoll >= 1 && secondRoll <= 6, "Second roll out of range: "
        + secondRoll);

    assertEquals(secondRoll, die.getValue(), "getValue didn't return the most recent roll");
  }

}
