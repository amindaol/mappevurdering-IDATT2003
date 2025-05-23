package edu.ntnu.idi.idatt.model.game;

import java.util.Random;

/**
 * Represents a single die with 6 sides.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class Die {

  private static final Random random = new Random();
  private static final int sides = 6;

  private int value;


  /**
   * Rolls the die and gives it a random value between 1 and 6.
   *
   * @return the value of the die after rolling.
   */
  public int roll() {
    this.value = random.nextInt(sides) + 1;
    return this.value;
  }


  /**
   * Returns the value of the die.
   *
   * @return the value of the die.
   * @throws IllegalStateException if the die has not been rolled yet.
   */
  public int getValue() {
    if (value == 0) {
      throw new IllegalStateException("Die has not been rolled yet.");
    }
    return this.value;
  }
}
