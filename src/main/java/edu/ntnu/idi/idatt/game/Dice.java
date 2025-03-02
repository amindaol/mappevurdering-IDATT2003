package edu.ntnu.idi.idatt.game;

import java.util.List;

public class Dice {

  private Die[] dice;

  public Dice(int diceAmount) {
    dice = new Die[diceAmount];
    for (int i = 0; i < diceAmount; i++) {
      dice[i] = new Die();
    }
  }

}
