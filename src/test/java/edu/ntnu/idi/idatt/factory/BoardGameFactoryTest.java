package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.game.BoardGame;
import edu.ntnu.idi.idatt.game.Tile;
import edu.ntnu.idi.idatt.game.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameFactoryTest {

  @TempDir
  Path tempDir;

  @Test
  void testCreateStandardBoardGame() {
    BoardGame game = BoardGameFactory.createStandardBoardGame();
    assertNotNull(game, "Factory must return a non-null game");
    assertNotNull(game.getBoard(), "Standard game should have a board");
    int size = game.getBoard().size();
    assertTrue(size > 0, "Standard board should contain at least one tile");
    assertTrue(game.getPlayers().isEmpty(), "Standard game should start with no players");
  }

  @Test
  void testCreateGameFromConfig() throws Exception {
    // Arrange: create minimal board JSON and players CSV
    Path boardJson = tempDir.resolve("board.json");
    String boardContent = """
        {
          "tiles": [
            { "id": 1, "nextTile": 2 },
            { "id": 2, "nextTile": 3 },
            { "id": 3 }
          ]
        }
        """;
    Files.writeString(boardJson, boardContent);

    Path playersCsv = tempDir.resolve("players.csv");
    String playersContent = "Alice,TopHat\nBob,RaceCar\n";
    Files.writeString(playersCsv, playersContent);

    // Act
    BoardGame game = BoardGameFactory.createGameFromConfig(boardJson, playersCsv);

    // Assert board structure
    assertNotNull(game.getBoard(), "Config-based game should have a board");
    assertEquals(3, game.getBoard().size(), "Board should have exactly 3 tiles");
    Tile t1 = game.getBoard().getTile(1);
    Tile t2 = game.getBoard().getTile(2);
    assertNotNull(t1.getNextTile(), "Tile 1 should link to tile 2");
    assertEquals(2, t1.getNextTile().getTileId());
    assertNotNull(t2.getNextTile(), "Tile 2 should link to tile 3");
    assertEquals(3, t2.getNextTile().getTileId());

    // Assert players
    List<Player> players = game.getPlayers();
    assertEquals(2, players.size(), "Should load two players");
    assertEquals("Alice", players.get(0).getName());
    assertEquals("TopHat", players.get(0).getToken());
    assertEquals("Bob", players.get(1).getName());
    assertEquals("RaceCar", players.get(1).getToken());
  }
}
