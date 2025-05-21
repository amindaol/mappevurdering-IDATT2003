package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Thrown when a player attempts an invalid move according to game rules.
 */
public class InvalidMoveException extends RuntimeException {

  /**
   * Constructs a new InvalidMoveException with the default message.
   *
   * @param message detail message explaining the invalid move
   */
  public InvalidMoveException(String message) {
    super(message);
  }

}
