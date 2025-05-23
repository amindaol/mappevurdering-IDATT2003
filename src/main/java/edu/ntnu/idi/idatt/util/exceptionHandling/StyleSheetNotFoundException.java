package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Exception thrown when a stylesheet cannot be found at the specified path.
 *
 * @author Aminda Lunde
 * @version 1.0
 */
public class StyleSheetNotFoundException extends RuntimeException {

  /**
   * Constructs a new StylesheetNotFoundException with the specified detail message.
   *
   * @param message the detail message explaining the exception
   */
  public StyleSheetNotFoundException(String message) {
    super(message);
  }
}