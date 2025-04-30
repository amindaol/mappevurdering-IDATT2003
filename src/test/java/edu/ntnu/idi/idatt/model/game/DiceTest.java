package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Die;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DiceTest {

  @Test
  void constructor_createsCorrectNumberOfDice() {
    Dice dice = new Dice(2);
    Die die = dice.getDie(0);
    assertNotNull(die);
  }

  @Test
  void roll_returnsSumInValidRange() {
    Dice dice = new Dice(2);
    int result = dice.roll();
    assertTrue(result >= 2 && result <= 12);
  }

  @Test
  void getDie_returnsDie() {
    Dice dice = new Dice(2);
    assertNotNull(dice.getDie(0));
  }

  @Test
  void getDie_invalidIndex_throwsException() {
    Dice dice = new Dice(2);
    assertThrows(IllegalArgumentException.class, () -> dice.getDie(5));
  }

  @Test
  void constructorZeroDiceThrowsIAE() {
    assertThrows(IllegalArgumentException.class, () -> new Dice(0));
  }

  @Test
  void getDieOutOfBoundsThrowsIAE() {
    Dice dice = new Dice(2);
    assertThrows(IllegalArgumentException.class, () -> dice.getDie(-1));
    assertThrows(IllegalArgumentException.class, () -> dice.getDie(2));
  }


}
