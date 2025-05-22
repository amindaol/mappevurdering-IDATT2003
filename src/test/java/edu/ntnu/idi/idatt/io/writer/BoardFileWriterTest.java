package edu.ntnu.idi.idatt.io.writer;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import edu.ntnu.idi.idatt.factory.BoardFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class BoardFileWriterGsonTest {

  private final BoardFileWriterGson writer = new BoardFileWriterGson();

  @Test
  void testWriteBoardCreatesFile() throws IOException {
    Board board = BoardFactory.createLoveAndLaddersBoard();
    Path tempFile = Files.createTempFile("board-test", ".json");

    try {
      writer.writeBoard(tempFile, board);

      assertTrue(Files.exists(tempFile));
      String content = Files.readString(tempFile);
      assertTrue(content.contains("\"tiles\""));
    } catch (DaoException e) {
      fail("Unexpected DaoException: " + e.getMessage());
    } finally {
      Files.deleteIfExists(tempFile);
    }
  }

  @Test
  void testWriteBoardThrowsExceptionOnInvalidPath() {
    Board board = BoardFactory.createLoveAndLaddersBoard();
    Path invalidPath = Path.of("/invalid_path/test.json");

    assertThrows(DaoException.class, () -> writer.writeBoard(invalidPath, board));
  }
}
