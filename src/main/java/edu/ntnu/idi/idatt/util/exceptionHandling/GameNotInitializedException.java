package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Thrown when attempting to perform game actions before the game is fully initialized.
 */
public class GameNotInitializedException extends IllegalStateException {

  public GameNotInitializedException() {
    super("Game not initialized: Board and/or players are missing. ");
  }

}
