package edu.ntnu.idi.idatt.util.exceptionHandling;


/**
 * Exception thrown when a player is expected to be on the game board (on a tile),
 * but is found to have no current tile assigned.
 * This indicates an invalid game state where the player is not properly placed on the board.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class PlayerNotOnBoardException extends IllegalStateException {

  /**
   * Constructs a new PlayerNotOnBoardException with the specified detail message.
   *
   * @param message the detail message explaining the exception
   */
  public PlayerNotOnBoardException(String message) {
    super(message);
  }

  /**
   * Constructs a new PlayerNotOnBoardException with the specified detail message
   * and cause.
   *
   * @param message the detail message explaining the exception
   * @param cause the cause of this exception
   */
  public PlayerNotOnBoardException(String message, Throwable cause) {
    super(message, cause);
  }
}
