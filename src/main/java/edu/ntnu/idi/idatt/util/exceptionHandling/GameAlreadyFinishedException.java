package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Thrown when attempting to play a turn after the game has already finished.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class GameAlreadyFinishedException extends IllegalStateException {

  /**
   * Constructs a new GameAlreadyFinishedException with the default message.
   */
  public GameAlreadyFinishedException() {
    super("Cannot play turn: the game has already finished. ");
  }


  /**
   * Constructs a new GameAlreadyFinishedException with a custom message.
   *
   * @param message the custom message
   */
  public GameAlreadyFinishedException(String message) {
    super(message);
  }

  /**
   * Constructs a new GameAlreadyFinishedException with a message and cause.
   *
   * @param message the custom message
   * @param cause the underlying cause
   */
  public GameAlreadyFinishedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new GameAlreadyFinishedException with a cause.
   *
   * @param cause the underlying cause
   */
  public GameAlreadyFinishedException(Throwable cause) {
    super(cause);
  }
}
