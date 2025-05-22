package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Thrown when attempting to play a turn without any players added.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class NoPlayersException extends RuntimeException {

  /**
   * Constructs a new NoPlayersException with the default message.
   */
  public NoPlayersException() {
    super("Cannot play turn: No players have been added to the game. ");
  }

}
