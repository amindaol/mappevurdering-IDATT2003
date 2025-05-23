package edu.ntnu.idi.idatt.util.exceptionHandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GameAlreadyFinishedExceptionTest {

  @Test
  void testDefaultConstructor() {
    GameAlreadyFinishedException ex = new GameAlreadyFinishedException();
    assertEquals("Cannot play turn: the game has already finished. ", ex.getMessage());
  }

  @Test
  void testMessageConstructor() {
    GameAlreadyFinishedException ex = new GameAlreadyFinishedException("Custom message");
    assertEquals("Custom message", ex.getMessage());
  }

  @Test
  void testCauseConstructor() {
    Throwable cause = new RuntimeException("Root cause");
    GameAlreadyFinishedException ex = new GameAlreadyFinishedException(cause);
    assertEquals(cause, ex.getCause());
  }

  @Test
  void testMessageAndCauseConstructor() {
    Throwable cause = new RuntimeException("Root cause");
    GameAlreadyFinishedException ex = new GameAlreadyFinishedException("Custom message", cause);
    assertEquals("Custom message", ex.getMessage());
    assertEquals(cause, ex.getCause());
  }
}