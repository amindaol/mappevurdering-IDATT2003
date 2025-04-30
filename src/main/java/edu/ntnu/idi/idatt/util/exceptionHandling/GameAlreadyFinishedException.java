package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Thrown when attempting to play a turn after the game has already finished.
 */
public class GameAlreadyFinishedException extends IllegalStateException {

  public GameAlreadyFinishedException() {
    super("Cannot play turn: the game has already finished. ");
  }

}
