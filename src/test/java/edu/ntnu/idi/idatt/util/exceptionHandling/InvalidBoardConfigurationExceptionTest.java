package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidBoardConfigurationExceptionTest {

  @Test
  void testInvalidBoardConfigurationException() {
    Exception exception = assertThrows(InvalidBoardConfigurationException.class, () -> {
      throw new InvalidBoardConfigurationException("Invalid board configuration.");
    });

    assertEquals("Invalid board configuration.", exception.getMessage());
  }
}