package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;

/**
 * Represents an action that does nothing. This action is used when a player lands on a tile that
 * has no action associated with it.
 */
public class NoAction implements TileAction {

  /**
   * Performs the action, which does nothing.
   */
  @Override
  public void perform(Player player) {
    //Do nothing
  }


}
