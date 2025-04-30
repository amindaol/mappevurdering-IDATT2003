package edu.ntnu.idi.idatt.util.ExceptionHandling;

/**
 * Thrown when attempting to look up a player that does not exist in the game.
 */
public class PlayerNotFoundException extends RuntimeException {

  public PlayerNotFoundException(String playerName) {
    super("player not found: " + playerName);
  }



}
