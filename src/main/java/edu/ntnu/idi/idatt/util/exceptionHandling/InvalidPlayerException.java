package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Exception thrown when a player is invalid (e.g., missing name or token).
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class InvalidPlayerException extends RuntimeException {

  /**
   * Constructs a new InvalidPlayerException with the specified detail message.
   *
   * @param message the detail message explaining the exception
   */
  public InvalidPlayerException(String message) {
    super(message);
  }
}