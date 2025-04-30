package edu.ntnu.idi.idatt.util.ExceptionHandling;

/**
 * Thrown when attempting to add more players than the allowed maximum.
 */
public class TooManyPlayersException extends RuntimeException {

  public TooManyPlayersException(int maxPlayers) {
    super("Cannot add more than " + maxPlayers + " players to the game. ");
  }

}
