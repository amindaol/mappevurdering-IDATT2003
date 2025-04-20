package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PlayerCsvDao}.
 */
class PlayerCsvDaoTest {

  @TempDir
  Path tempDir;
  private Path csvFile;
  private PlayerCsvDao dao;

  @BeforeEach
  void setUp() throws IOException {
    csvFile = tempDir.resolve("players.csv");
    Files.createFile(csvFile);
    dao = new PlayerCsvDao(csvFile);
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(csvFile);
  }

  @Test
  void testWriteAndReadPlayers() {
    List<Player> input = List.of(
        new Player("Arne", "TopHat"),
        new Player("Ivar", "RaceCar")
    );

    dao.writePlayers(input);

    List<Player> output = dao.readPlayers();
    assertEquals(2, output.size(), "Should read two players");

    Player p0 = output.get(0);
    assertEquals("Arne", p0.getName());
    assertEquals("TopHat", p0.getToken());

    Player p1 = output.get(1);
    assertEquals("Ivar", p1.getName());
    assertEquals("RaceCar", p1.getToken());
  }

  @Test
  void testReadInvalidCsvLineThrowsDaoException() throws IOException {
    Files.writeString(csvFile, "InvalidLineWithoutComma\n");
    assertThrows(DaoException.class, () -> dao.readPlayers());
  }

  @Test
  void testReadNonexistentFileThrowsDaoException() {
    Path missing = tempDir.resolve("does_not_exist.csv");
    PlayerCsvDao missingDao = new PlayerCsvDao(missing);
    assertThrows(DaoException.class, missingDao::readPlayers);
  }
}
