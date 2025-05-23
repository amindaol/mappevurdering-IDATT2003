package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import edu.ntnu.idi.idatt.model.game.Player;

/**
 * Adds a fixed number of coins to the player.
 */
public class AddCoinsAction implements TileAction {

  private final int amount;

  /**
   * Constructor for AddCoinsAction.
   *
   * @param amount the number of coins to add
   */
  public AddCoinsAction(int amount) {
    this.amount = amount;
  }

  /**
   * Performs the action of adding coins to the player.
   */
  @Override
  public void perform(Player player) {
    if (player instanceof BestiePlayer bp) {
      bp.addCoins(amount);
    }
  }
}
