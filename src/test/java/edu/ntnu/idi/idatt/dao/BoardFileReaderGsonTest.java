package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Board;
import edu.ntnu.idi.idatt.game.LadderAction;
import edu.ntnu.idi.idatt.game.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class BoardFileReaderGsonTest {

  @TempDir
  Path tempDir;
  private Path jsonFile;
  private BoardFileReaderGson reader;

  @BeforeEach
  void setUp() {
    jsonFile = tempDir.resolve("board.json");
    reader = new BoardFileReaderGson();
  }

  @Test
  void testReadValidJson() throws Exception {
    String json = """
        {
          "tiles": [
            { "id": 1, "nextTile": 2 },
            { "id": 2, "nextTile": 3, "action": {
                "type": "LadderAction",
                "destinationTileId": 5,
                "description": "Ladder to 5"
              }
            },
            { "id": 3 }
          ]
        }
        """;
    Files.writeString(jsonFile, json);

    Board board = reader.readBoard(jsonFile);
    assertEquals(3, board.size(), "Should read three tiles");

    Tile tile2 = board.getTile(2);
    assertNotNull(tile2.getLandAction(), "Tile 2 should have an action");
    assertTrue(tile2.getLandAction() instanceof LadderAction);
    LadderAction la = (LadderAction) tile2.getLandAction();
    assertEquals(5, la.getDestinationTileId());
    assertEquals("Ladder to 5", la.getDescription());
  }

  @Test
  void testReadMissingTilesThrows() throws Exception {
    Files.writeString(jsonFile, "{ \"noTiles\": [] }");
    assertThrows(InvalidJsonFormatException.class,
        () -> reader.readBoard(jsonFile));
  }
}