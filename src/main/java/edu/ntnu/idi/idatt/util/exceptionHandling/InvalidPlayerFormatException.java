package edu.ntnu.idi.idatt.util.exceptionHandling;


/**
 * Exception thrown when a player CSV line is malformed or
 * contains invalid data during parsing.
 * <p>
 * This is a domain-specific unchecked exception used
 * to signal errors in player data format.
 * </p>
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class InvalidPlayerFormatException extends IllegalArgumentException {

  /**
   * Constructs a new InvalidPlayerFormatException with the specified detail message.
   *
   * @param message the detail message explaining the parsing error
   */
  public InvalidPlayerFormatException(String message) {
    super(message);
  }

  /**
   * Constructs a new InvalidPlayerFormatException with the specified detail message
   * and cause.
   *
   * @param message the detail message explaining the parsing error
   * @param cause the cause of this exception
   */
  public InvalidPlayerFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}