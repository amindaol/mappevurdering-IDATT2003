package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Thrown when attempting to perform game actions before the game is fully initialized.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class GameNotInitializedException extends IllegalStateException {

  /**
   * Constructs a new GameNotInitializedException with the default message.
   */
  public GameNotInitializedException(String s) {
    super("Game not initialized: Board and/or players are missing. ");
  }

}
