package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.BestiePlayer;

/**
 * Adds a fixed number of coins to the player.
 */
public class AddCoinsAction implements TileAction {

  private final int amount;

  public AddCoinsAction(int amount) {
    this.amount = amount;
  }

  @Override
  public void perform(Player player) {
    if (player instanceof BestiePlayer bp) {
      bp.addCoins(amount);
    }
  }
}
