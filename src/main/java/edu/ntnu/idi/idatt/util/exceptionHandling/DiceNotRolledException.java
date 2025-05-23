package edu.ntnu.idi.idatt.util.exceptionHandling;


/**
 * Exception thrown when an attempt is made to access the roll value of the dice before they have been rolled.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class DiceNotRolledException extends IllegalStateException {

  /**
   * Constructs a new DiceNotRolledException with the specified detail message.
   *
   * @param message the detail message explaining the exception
   */
  public DiceNotRolledException(String message) {
    super(message);
  }
}

