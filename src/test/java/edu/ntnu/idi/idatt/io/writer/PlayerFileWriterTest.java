package edu.ntnu.idi.idatt.io.writer;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFileWriterCsvTest {

  private final PlayerFileWriterCsv writer = new PlayerFileWriterCsv();

  @Test
  void testWritePlayersCreatesCsvFile() throws IOException {
    Player player1 = new Player("Aminda", new Token("Heart", "heart.png"), LocalDate.of(2005, 11, 5));
    Player player2 = new Player("Ingrid", new Token("Star", "star.png"), LocalDate.of(2002, 8, 28));
    List<Player> players = List.of(player1, player2);

    Path tempFile = Files.createTempFile("players", ".csv");

    try {
      writer.writePlayers(tempFile, players);

      List<String> lines = Files.readAllLines(tempFile);
      assertEquals(2, lines.size());
      assertEquals("Aminda,heart.png", lines.get(0));
      assertEquals("Ingrid,star.png", lines.get(1));
    } catch (DaoException e) {
      fail("Unexpected DaoException: " + e.getMessage());
    } finally {
      Files.deleteIfExists(tempFile);
    }
  }

  @Test
  void testWritePlayersThrowsExceptionOnInvalidPath() {
    Player player = new Player("Aminda", new Token("Heart", "heart.png"), LocalDate.of(2005, 11, 5));
    Path invalidPath = Path.of("/invalid_path/test.csv");

    assertThrows(DaoException.class, () -> writer.writePlayers(invalidPath, List.of(player)));
  }
}
