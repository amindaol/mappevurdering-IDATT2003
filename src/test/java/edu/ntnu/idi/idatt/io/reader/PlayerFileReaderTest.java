package edu.ntnu.idi.idatt.io.reader;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class PlayerFileReaderTest {

  private final PlayerFileReaderCsv reader = new PlayerFileReaderCsv();
  private final Path validPath = Path.of("src/main/resources/players/test-players.csv");
  private final Path invalidPath = Path.of("src/test/resources/players/nonexistent.csv");
  private final Path malformedPath = Path.of("src/main/resources/players/bad-players.csv");

  @Test
  void testReadPlayersReturnsCorrectList() {
    try {
      List<Player> players = reader.readPlayers(validPath);
      assertEquals(2, players.size());

      Player p1 = players.get(0);
      assertEquals("Aminda", p1.getName());
      assertEquals("flower.png", p1.getToken().getIconFileName());
      assertEquals(2005, p1.getBirthday().getYear());

    } catch (DaoException e) {
      fail("Should not have thrown: " + e.getMessage());
    }
  }

  @Test
  void testReadPlayersThrowsOnMissingFile() {
    assertThrows(DaoException.class, () -> reader.readPlayers(invalidPath));
  }

  @Test
  void testReadPlayersThrowsOnMalformedLine() {
    assertThrows(DaoException.class, () -> reader.readPlayers(malformedPath));
  }
}
