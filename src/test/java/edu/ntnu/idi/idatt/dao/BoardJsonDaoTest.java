package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Board;
import edu.ntnu.idi.idatt.game.Tile;
import edu.ntnu.idi.idatt.game.LadderAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link BoardJsonDao}.
 */
class BoardJsonDaoTest {

  @TempDir
  Path tempDir;
  private Path jsonFile;
  private BoardJsonDao dao;

  @BeforeEach
  void setUp() throws IOException {
    jsonFile = tempDir.resolve("board.json");
    dao = new BoardJsonDao(jsonFile);
  }

  @Test
  void testReadBoardValidJson() throws IOException {
    String json = "{\n" +
        "  \"tiles\": [\n" +
        "    { \"id\": 1, \"nextTile\": 2 },\n" +
        "    { \"id\": 2, \"nextTile\": 3, \"action\": {\n" +
        "        \"type\": \"LadderAction\",\n" +
        "        \"destinationTileId\": 5,\n" +
        "        \"description\": \"Ladder to 5\"\n" +
        "      }\n" +
        "    },\n" +
        "    { \"id\": 3 }\n" +
        "  ]\n" +
        "}";
    Files.writeString(jsonFile, json);

    Board board = dao.readBoard();
    // Validate number of tiles
    assertEquals(3, board.size());
    // Validate nextTile linkage
    Tile tile1 = board.getTile(1);
    assertNotNull(tile1.getNextTile());
    assertEquals(2, tile1.getNextTile().getTileId());

    // Validate action on tile 2
    Tile tile2 = board.getTile(2);
    assertNotNull(tile2.getLandAction());
    assertTrue(tile2.getLandAction() instanceof LadderAction);
    LadderAction action = (LadderAction) tile2.getLandAction();
    assertEquals(5, action.getDestinationTileId());
    assertEquals("Ladder to 5", action.getDescription());
  }

  @Test
  void testReadBoardMissingTilesThrowsException() throws IOException {
    String invalidJson = "{ \"name\": \"NoTiles\" }";
    Files.writeString(jsonFile, invalidJson);
    InvalidJsonFormatException ex = assertThrows(
        InvalidJsonFormatException.class,
        () -> dao.readBoard()
    );
    assertTrue(ex.getMessage().contains("Missing 'tiles' array"));
  }

  @Test
  void testReadBoardMalformedJsonThrowsException() throws IOException {
    String badJson = "{ tiles: [ }";
    Files.writeString(jsonFile, badJson);
    InvalidJsonFormatException ex = assertThrows(
        InvalidJsonFormatException.class,
        () -> dao.readBoard()
    );
    assertTrue(ex.getMessage().contains("Malformed JSON"));
  }
}
