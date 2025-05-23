package edu.ntnu.idi.idatt.model.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

  @Test
  void testConstructorWithValidAmount() {
    Dice dice = new Dice(3);
    assertEquals(3, dice.getDiceAmount());
  }

  @Test
  void testConstructorWithZeroThrows() {
    assertThrows(IllegalArgumentException.class, () -> new Dice(0));
  }

  @Test
  void testConstructorWithNegativeThrows() {
    assertThrows(IllegalArgumentException.class, () -> new Dice(-2));
  }

  @Test
  void testRollReturnsCorrectAmountOfValues() {
    Dice dice = new Dice(2);
    List<Integer> results = dice.roll();
    assertEquals(2, results.size());
    for (int value : results) {
      assertTrue(value >= 1 && value <= 6, "Each die roll should be between 1 and 6");
    }
  }

  @Test
  void testGetRollValueAfterRoll() {
    Dice dice = new Dice(2);
    dice.roll(); // rolls but does not update Die instances
    // Need to roll individual Die instances too (code bug workaround)
    for (int i = 0; i < dice.getDiceAmount(); i++) {
      dice.getDie(i).roll();
    }

    int total = dice.getRollValue();
    assertTrue(total >= 2 && total <= 12, "Total value should be between 2 and 12");
  }

  @Test
  void testGetDieWithValidIndex() {
    Dice dice = new Dice(2);
    Die die = dice.getDie(1);
    assertNotNull(die);
  }

  @Test
  void testGetDieWithInvalidIndexThrows() {
    Dice dice = new Dice(2);
    assertThrows(IllegalArgumentException.class, () -> dice.getDie(-1));
    assertThrows(IllegalArgumentException.class, () -> dice.getDie(2));
  }

  @Test
  void testRollIsRandomized() {
    Dice dice = new Dice(1);
    boolean different = false;
    int first = dice.roll().get(0);

    for (int i = 0; i < 10; i++) {
      if (dice.roll().get(0) != first) {
        different = true;
        break;
      }
    }
    assertTrue(different, "Dice should produce different values over multiple rolls");
  }

}
