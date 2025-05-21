package edu.ntnu.idi.idatt.factory;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Unit tests for {@link BoardGameFactory}.
 */
class BoardGameFactoryTest {

  @TempDir
  Path tempDir;

  @Test
  void testCreateGameFromConfigValid() throws Exception {
    Path boardJson = tempDir.resolve("board.json");
    String json = "{" +
        "\"rows\":1,\"cols\":2,\"specialTiles\":[]" +
        "}";
    Files.writeString(boardJson, json);

    Path playersCsv = tempDir.resolve("players.csv");
    String csv = "Alice,hat\nBob,boot\n";
    Files.writeString(playersCsv, csv);

    //BoardGame game = BoardGameFactory.createGameFromConfig(boardJson, playersCsv);

    //assertNotNull(game, "Game should not be null");
    //assertEquals(2, game.getBoard().size(), "Board should have 2 tiles");

//    List<Player> players = game.getPlayers();
//    assertEquals(2, players.size(), "Should load 2 players");
//    assertEquals("Alice", players.get(0).getName());
//    assertEquals("hat",   players.get(0).getToken());
//    assertEquals("Bob",   players.get(1).getName());
//    assertEquals("boot",  players.get(1).getToken());
  }

//  @Test
//  void testCreateGameFromConfigBadJson() throws Exception {
//    Path boardJson = tempDir.resolve("board.json");
//    Files.writeString(boardJson, "not a json");
//    Path playersCsv = tempDir.resolve("players.csv");
//    Files.writeString(playersCsv, "Alice,hat");
//
//    assertThrows(DaoException.class,
//        () -> BoardGameFactory.createGameFromConfig(boardJson, playersCsv),
//        "Invalid JSON should cause DaoException"
//    );
//  }
//
//  @Test
//  void testCreateGameFromConfigBadCsv() throws Exception {
//    Path boardJson = tempDir.resolve("board.json");
//    String json = "{" +
//        "\"rows\":1,\"cols\":1,\"specialTiles\":[]" +
//        "}";
//    Files.writeString(boardJson, json);
//
//    Path playersCsv = tempDir.resolve("players.csv");
//    Files.writeString(playersCsv, "invalid_line_without_comma");
//
//    assertThrows(DaoException.class,
//        () -> BoardGameFactory.createGameFromConfig(boardJson, playersCsv),
//        "Invalid CSV should cause DaoException"
//    );
//  }
}