package edu.ntnu.idi.idatt.game;

import java.util.List;

/**
 * The {@code Dice} class represents a set of dice. It allows rolling all dice at once and accessing
 * individual dice if needed.
 *
 * <p>This class works with the {@link Die} class, where each die represents a single 6 sided
 * die. </\p>
 */
public class Dice {

  private Die[] dice;

  /**
   * Constructs a {@code Dice} object with a specified number of dice. Each die is created and
   * stored in an internal array.
   *
   * @param diceAmount The number of dice to create in this set.
   */
  public Dice(int diceAmount) {
    dice = new Die[diceAmount];
    for (int i = 0; i < diceAmount; i++) {
      dice[i] = new Die();
    }
  }

  /**
   * Rolls all the dice in this set. Each die rolls independently, and the total sum of all rolls is
   * returned.
   *
   * @return The sum of all dice rolls.
   */
  public int roll() {
    int diceValue = 0;
    for (Die d : dice) {
      diceValue += d.roll();
    }
    return diceValue;
  }

  /**
   * Retrieves a specific die from the set.
   *
   * @param dieNumber The index of the die to retrieve (0-based).
   * @return The {@code Die} object at the specified position.
   */
  public Die getDie(int dieNumber) {
    return dice[dieNumber];
  }

}
