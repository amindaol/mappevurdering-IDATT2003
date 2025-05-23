package edu.ntnu.idi.idatt.util.exceptionHandling;


/**
 * Exception thrown when a board file is not found.
 * This is used when the game cannot find the specified board file.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BoardFileNotFoundException extends RuntimeException {

  /**
   * Constructs a new BoardFileNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public BoardFileNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs a new BoardFileNotFoundException with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   */
  public BoardFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}