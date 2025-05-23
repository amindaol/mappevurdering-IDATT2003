package edu.ntnu.idi.idatt.model.core;

import edu.ntnu.idi.idatt.model.game.*;
import edu.ntnu.idi.idatt.util.exceptionHandling.PlayerNotOnBoardException;

/**
 * Represents a linear movement strategy for a player in the game. This class implements the
 * Movement interface and provides a method to move a player linearly across the game board.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class LinearMovement implements Movement {

  /**
   * Moves the player a specified number of steps along the game board. The player will move to the
   * next tile for each step taken. If the player reaches the end of the board, they will stop
   * moving.
   *
   * @param player the player to move
   * @param steps  the number of steps to move the player
   * @throws NullPointerException      if {@code player} is null
   * @throws IllegalArgumentException  if {@code steps} is negative
   * @throws PlayerNotOnBoardException if {@code player} is not currently on any tile
   */
  @Override
  public void move(Player player, int steps) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null.");
    }
    if (steps < 0) {
      throw new IllegalArgumentException("Steps cannot be negative.");
    }

    Tile current = player.getCurrentTile();
    if (current == null) {
      throw new PlayerNotOnBoardException("Player's current tile is null.");
    }

    Tile destination = current;
    for (int i = 0; i < steps && destination.getNextTile() != null; i++) {
      destination = destination.getNextTile();
    }

    player.setCurrentTile(destination);

    if (destination.getAction() != null) {
      destination.getAction().perform(player);
    }
  }
}
