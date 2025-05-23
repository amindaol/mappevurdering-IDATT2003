package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;

/**
 * Represents an action that occurs when a player lands on a tile. This interface is used to define
 * various actions that can be performed
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public interface TileAction {

  /**
   * Performs an action on the given player when they land on a tile.
   *
   * @param player the player who landed on the tile
   */
  void perform(Player player);
}
