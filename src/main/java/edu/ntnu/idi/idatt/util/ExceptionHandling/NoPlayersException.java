package edu.ntnu.idi.idatt.util.ExceptionHandling;

/**
 * Thrown when attempting to play a turn without any players added.
 */
public class NoPlayersException extends RuntimeException{

  public NoPlayersException() {
    super("Cannot play turn: No players have been added to the game. ");
  }

}
