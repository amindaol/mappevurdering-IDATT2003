package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Thrown when attempting to look up a player that does not exist in the game.
 */
public class PlayerNotFoundException extends RuntimeException {

  /**
   * Constructs a new PlayerNotFoundException with the default message.
   *
   * @param playerName the name of the player that was not found
   */
  public PlayerNotFoundException(String playerName) {
    super("player not found: " + playerName);
  }


}
