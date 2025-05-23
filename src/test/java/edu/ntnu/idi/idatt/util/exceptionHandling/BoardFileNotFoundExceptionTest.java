package edu.ntnu.idi.idatt.util.exceptionHandling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardFileNotFoundExceptionTest {

  @Test
  void testBoardFileNotFoundException() {
    Exception exception = assertThrows(BoardFileNotFoundException.class, () -> {
      throw new BoardFileNotFoundException("Board file not found.");
    });

    assertEquals("Board file not found.", exception.getMessage());
  }
}