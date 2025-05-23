package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Thrown when attempting to add more players than the allowed maximum.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class TooManyPlayersException extends RuntimeException {

  /**
   * Constructs a new TooManyPlayersException with the default message.
   *
   * @param maxPlayers the maximum number of players allowed in the game
   */
  public TooManyPlayersException(int maxPlayers) {
    super("Cannot add more than " + maxPlayers + " players to the game. ");
  }

}
