package edu.ntnu.idi.idatt.util.exceptionHandling;


/**
 * Exception thrown when there is an error displaying an alert in the JavaFX application.
 *
 * @author Aminda Lunde
 * @version 1.0
 */
public class AlertDisplayException extends RuntimeException {

  /**
   * Constructs a new AlertDisplayException with the specified detail message.
   *
   * @param message the detail message explaining the exception
   */
  public AlertDisplayException(String message) {
    super(message, cause);
  }
}