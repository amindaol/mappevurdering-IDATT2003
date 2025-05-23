package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Exception thrown to indicate that a specified game mode
 * is not supported or recognized by the system.
 * This is a domain-specific unchecked exception used
 * to signal invalid game mode usage.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class UnsupportedGameModeException extends RuntimeException {

  /**
   * Constructs a new UnsupportedGameModeException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception
   */
  public UnsupportedGameModeException(String message) {
    super(message);
  }

  /**
   * Constructs a new UnsupportedGameModeException with the specified detail message
   * and cause.
   *
   * @param message the detail message explaining the reason for the exception
   * @param cause the cause (which is saved for later retrieval)
   */
  public UnsupportedGameModeException(String message, Throwable cause) {
    super(message, cause);
  }
}
