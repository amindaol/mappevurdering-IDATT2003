package edu.ntnu.idi.idatt.game;

import java.util.List;

/**
 *
 */
public class Dice {

  private Die[] dice;

  /**
   * @param diceAmount
   */
  public Dice(int diceAmount) {
    dice = new Die[diceAmount];
    for (int i = 0; i < diceAmount; i++) {
      dice[i] = new Die();
    }
  }

  /**
   * @return
   */
  public int roll() {
    int diceValue = 0;
    for (Die d : dice) {
      diceValue += d.roll();
    }
    return diceValue;
  }

  /**
   * @param dieNumber
   * @return
   */
  public Die getDie(int dieNumber) {
    return dice[dieNumber];
  }

}
