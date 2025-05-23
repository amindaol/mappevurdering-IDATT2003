package edu.ntnu.idi.idatt.util.exceptionHandling;

/**
 * Exception to be thrown when the board configuration is invalid.
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class InvalidBoardConfigurationException extends RuntimeException {

  /**
   * Constructs a new InvalidBoardConfigurationException with the default message.
   */
  public InvalidBoardConfigurationException(String message) {
    super(message);
  }
}