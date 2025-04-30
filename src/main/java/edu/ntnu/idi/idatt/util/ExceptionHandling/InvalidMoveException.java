package edu.ntnu.idi.idatt.util.ExceptionHandling;

/**
 * Thrown when a player attempts an invalid move according to game rules.
 */
public class InvalidMoveException extends RuntimeException {

  public InvalidMoveException(String message) {
    super(message);
  }

}
