package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Thrown when JSON data cannot be parsed into a valid Board structure.
 */
public class InvalidJsonFormatException extends DaoException {

  /**
   * Constructs a new exception indicating malformed JSON.
   *
   * @param message detail message explaining the parsing error
   * @param cause   the underlying exception (e.g., JsonParseException), or null
   */
  public InvalidJsonFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}