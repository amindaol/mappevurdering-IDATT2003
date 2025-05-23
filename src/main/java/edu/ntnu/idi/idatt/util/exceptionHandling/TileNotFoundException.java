package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Exception thrown when a tile is not found on the game board.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class TileNotFoundException extends RuntimeException {

  /**
   * Constructs a new TileNotFoundException with the specified detail message.
   *
   * @param message the detail message explaining the exception
   */
  public TileNotFoundException(String message) {
    super(message);
  }
}
