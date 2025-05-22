package edu.ntnu.idi.idatt.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Dice} class represents a set of dice. It allows rolling all dice at once and accessing
 * individual dice if needed.
 *
 * This class works with the {@link Die} class, where each die represents a single 6 sided
 * die.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class Dice {

  private final Die[] dice;
  private final int diceAmount;

  /**
   * Constructs a {@code Dice} object with a specified number of dice. Each die is created and
   * stored in an internal array.
   *
   * @param dieAmount The number of dice to create in this set.
   * @throws IllegalArgumentException if {@code dieAmount} is less than or equal to zero.
   */
  public Dice(int dieAmount) {
    if (dieAmount <= 0) {
      throw new IllegalArgumentException("Number of dice must be positive.");
    }

    this.diceAmount = dieAmount;

    dice = new Die[dieAmount];
    for (int i = 0; i < dieAmount; i++) {
      dice[i] = new Die();
    }
  }

  /**
   * Rolls all the dice in this set. Each die rolls independently, and the total sum of all rolls is
   * returned.
   *
   * @return The sum of all dice rolls.
   */
  public List<Integer> roll() {
    List<Integer> results = new ArrayList<>();
    for (int i = 0; i < diceAmount; i++) {
      results.add((int) (Math.random() * 6) + 1);
    }
    return results;
  }

  /**
   * Returns the total value of the last roll of all dice. This method assumes that the dice have
   * already been rolled.
   *
   * @return The total value of all dice rolls. 0 if the dice have not been rolled.
   */
  public int getRollValue() {
    int total = 0;
    for (int i = 0; i < diceAmount; i++) {
      total += dice[i].getValue();
    }
    return total;
  }

  /**
   * Retrieves a specific die from the set.
   *
   * @param dieNumber The index of the die to retrieve (0-based).
   * @return The {@code Die} object at the specified position.
   * @throws IllegalArgumentException if {@code dieNumber} is negative or exceeds the number of
   *                                  dice
   */
  public Die getDie(int dieNumber) {
    if (dieNumber < 0 || dieNumber >= dice.length) {
      throw new IllegalArgumentException("Invalid die number: " + dieNumber);
    }
    return dice[dieNumber];
  }

  /**
   * Retrieves the number of dice in this set.
   *
   * @return The number of dice.
   */
  public int getDiceAmount() {
    return dice.length;
  }

}
