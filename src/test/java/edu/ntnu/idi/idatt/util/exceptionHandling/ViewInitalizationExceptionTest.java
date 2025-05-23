package edu.ntnu.idi.idatt.util.exceptionHandling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewInitializationExceptionTest {

  @Test
  void testViewInitializationException() {
    Exception exception = assertThrows(ViewInitializationException.class, () -> {
      throw new ViewInitializationException("View initialization failed.");
    });

    assertEquals("View initialization failed.", exception.getMessage());
  }
}