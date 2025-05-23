package edu.ntnu.idi.idatt.util.exceptionHandling;


/**
 * Exception thrown when no players are configured or players are configured incorrectly.
 * This is used when the game setup does not have valid players.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class PlayerNotConfiguredException extends RuntimeException {

  /**
   * Constructs a new PlayerNotConfiguredException with the specified detail message.
   *
   * @param message the detail message
   */
  public PlayerNotConfiguredException(String message) {
    super(message);
  }

  /**
   * Constructs a new PlayerNotConfiguredException with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   */
  public PlayerNotConfiguredException(String message, Throwable cause) {
    super(message, cause);
  }
}