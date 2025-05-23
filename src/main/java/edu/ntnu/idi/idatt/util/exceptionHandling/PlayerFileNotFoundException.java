package edu.ntnu.idi.idatt.util.exceptionHandling;


/**
 * Exception thrown when a player file is not found.
 * This is used when the game cannot find or open the specified player file.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class PlayerFileNotFoundException extends RuntimeException {

  /**
   * Constructs a new PlayerFileNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public PlayerFileNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs a new PlayerFileNotFoundException with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   */
  public PlayerFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}