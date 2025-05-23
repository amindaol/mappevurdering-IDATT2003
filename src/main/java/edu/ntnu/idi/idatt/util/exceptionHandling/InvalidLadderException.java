package edu.ntnu.idi.idatt.util.exceptionHandling;


/**
 * Exception thrown when an invalid ladder is encountered (e.g., if ladders list is null).
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class InvalidLadderException extends RuntimeException {

  /**
   * Constructs a new InvalidLadderException with the specified detail message.
   *
   * @param message the detail message explaining the exception
   */
  public InvalidLadderException(String message) {
    super(message);
  }
}