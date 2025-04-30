package edu.ntnu.idi.idatt.model.dao;

import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import edu.ntnu.idi.idatt.model.dao.PlayerFileReader;
import edu.ntnu.idi.idatt.model.dao.PlayerFileReaderCsv;
import edu.ntnu.idi.idatt.model.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFileReaderCsvTest {

  @TempDir
  Path tempDir;
  private Path csvFile;
  private PlayerFileReader reader;

  @BeforeEach
  void setUp() {
    csvFile = tempDir.resolve("players.csv");
    reader = new PlayerFileReaderCsv();
  }

  @Test
  void testReadValidCsv() throws Exception {
    String content = "Alice,TopHat\nBob,RaceCar\n";
    Files.writeString(csvFile, content);

    List<Player> players = reader.readPlayers(csvFile);
    assertEquals(2, players.size());
    assertEquals("Alice", players.get(0).getName());
    assertEquals("TopHat", players.get(0).getToken());
    assertEquals("Bob", players.get(1).getName());
    assertEquals("RaceCar", players.get(1).getToken());
  }

  @Test
  void testReadInvalidCsvLineThrows() throws IOException {
    Files.writeString(csvFile, "BadLineWithoutComma\n");
    assertThrows(DaoException.class, () -> reader.readPlayers(csvFile));
  }
}