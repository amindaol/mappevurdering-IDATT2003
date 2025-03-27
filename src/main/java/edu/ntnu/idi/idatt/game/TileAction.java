package edu.ntnu.idi.idatt.game;

/**
 * Represents an action that occurs when a player lands on a tile.
 */
public interface TileAction {

  /**
   * Performs an action on the given player when they land on a tile.
   *
   * @param player the player who landed on the tile
   */
  void perform(Player player);
}
