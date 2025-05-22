package edu.ntnu.idi.idatt.model.core;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;

/**
 * Represents a movement strategy for a player in the game. This interface is used to define how a
 * player moves on the board.
 */
public interface Movement {

  /**
   * Moves the player a specified number of steps along the game board. The player will move to the
   * next tile for each step taken. If the player reaches the end of the board, they will stop
   * moving.
   *
   * @param player the player to move
   * @param steps  the number of steps to move the player
   */
  void move(Player player, int steps);

}
