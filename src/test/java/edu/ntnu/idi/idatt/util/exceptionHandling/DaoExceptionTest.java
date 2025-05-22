package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DaoExceptionTest {

  @Test
  void testDefaultConstructor() {
    DaoException exception = new DaoException();
    assertNotNull(exception);
  }

  @Test
  void testMessageConstructor() {
    DaoException exception = new DaoException("DAO error");
    assertEquals("DAO error", exception.getMessage());
  }

  @Test
  void testCauseConstructor() {
    Throwable cause = new RuntimeException("Root cause");
    DaoException exception = new DaoException("With cause", cause);
    assertEquals("With cause", exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}