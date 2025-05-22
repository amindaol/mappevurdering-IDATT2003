package edu.ntnu.idi.idatt.model.core;

import edu.ntnu.idi.idatt.model.game.*;

/**
 * Represents a linear movement strategy for a player in the game. This class implements the
 * Movement interface and provides a method to move a player linearly across the game board.
 */
public class LinearMovement implements Movement {

  /**
   * Moves the player a specified number of steps along the game board. The player will move to the
   * next tile for each step taken. If the player reaches the end of the board, they will stop
   * moving.
   *
   * @param player the player to move
   * @param steps  the number of steps to move the player
   */
  @Override
  public void move(Player player, int steps) {
    Tile current = player.getCurrentTile();
    if (current == null) {
      throw new IllegalStateException("Player's current tile is null.");
    }

    Tile destination = current;
    for (int i = 0; i < steps && destination.getNextTile() != null; i++) {
      Tile next = destination.getNextTile();
      destination = next;
    }

    player.setCurrentTile(destination);

    if (destination.getAction() != null) {
      destination.getAction().perform(player);
    } else {
    }
  }
}
