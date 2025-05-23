package edu.ntnu.idi.idatt.util.exceptionHandling;


/**
 * Custom exception thrown when a view fails to initialize properly.
 * This exception can be used to handle errors during the initialization
 * of view components in the application.
 *
 * @author Aminda Lunde
 * @version 1.0
 */
public class ViewInitializationException extends RuntimeException {

  /**
   * Constructs a new ViewInitializationException with the specified detail message.
   *
   * @param message the detail message
   */
  public ViewInitializationException(String message) {
    super(message);
  }

  /**
   * Constructs a new ViewInitializationException with the specified detail message
   * and cause.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   */
  public ViewInitializationException(String message, Throwable cause) {
    super(message, cause);
  }
}