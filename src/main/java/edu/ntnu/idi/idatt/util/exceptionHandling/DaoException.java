package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Exception thrown when a data access operation fails.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class DaoException extends RuntimeException {

  /**
   * Constructs a new DaoException with the specified detail message and cause.
   *
   * @param message the detail message explaining the error
   * @param cause   the underlying cause of the error
   */
  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }
}