package edu.ntnu.idi.idatt.util.exceptionHandling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StyleSheetNotFoundExceptionTest {

  @Test
  void testStyleSheetNotFoundException() {
    Exception exception = assertThrows(StyleSheetNotFoundException.class, () -> {
      throw new StyleSheetNotFoundException("StyleSheet not found.");
    });

    assertEquals("StyleSheet not found.", exception.getMessage());
  }
}