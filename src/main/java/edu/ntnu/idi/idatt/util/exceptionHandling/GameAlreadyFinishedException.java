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

}
