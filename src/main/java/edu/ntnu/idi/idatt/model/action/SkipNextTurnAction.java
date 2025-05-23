package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;

/**
 * Represents an action that skips the player's next turn. This action is used when a player lands
 * on a tile that has a skip next turn action.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class SkipNextTurnAction implements TileAction {

  /**
   * Constructs a SkipNextTurnAction.
   *
   * @param player the player who will skip their next turn
   * @throws NullPointerException if {@code player} is {@code null}
   */
  @Override
  public void perform(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    player.setSkipNextTurn(true);
  }
}