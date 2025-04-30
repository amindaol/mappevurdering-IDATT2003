package edu.ntnu.idi.idatt.model.dao;

import edu.ntnu.idi.idatt.model.dao.PlayerFileReader;
import edu.ntnu.idi.idatt.model.dao.PlayerFileReaderCsv;
import edu.ntnu.idi.idatt.model.dao.PlayerFileWriter;
import edu.ntnu.idi.idatt.model.dao.PlayerFileWriterCsv;
import edu.ntnu.idi.idatt.model.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFileWriterCsvTest {

  @TempDir
  Path tempDir;
  private Path csvFile;
  private PlayerFileWriter writer;
  private PlayerFileReader reader;

  @BeforeEach
  void setUp() {
    csvFile = tempDir.resolve("out.csv");
    writer = new PlayerFileWriterCsv();
    reader = new PlayerFileReaderCsv();
  }

  @Test
  void testWriteAndReadRoundTrip() throws Exception {
    List<Player> players = List.of(
        new Player("Alice", "TopHat"),
        new Player("Bob", "RaceCar")
    );

    writer.writePlayers(csvFile, players);
    assertTrue(Files.exists(csvFile), "CSV file should be created");

    List<Player> loaded = reader.readPlayers(csvFile);
    assertEquals(players.size(), loaded.size());
    assertEquals("Alice", loaded.get(0).getName());
    assertEquals("TopHat", loaded.get(0).getToken());
    assertEquals("Bob", loaded.get(1).getName());
    assertEquals("RaceCar", loaded.get(1).getToken());
  }
}