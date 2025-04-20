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

    assertEquals(3, board.size());

    Tile tile1 = board.getTile(1);
    assertNotNull(tile1.getNextTile());
    assertEquals(2, tile1.getNextTile().getTileId());

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
  
  @Test
  void testWriteAndReadBoardRoundTrip() throws IOException {
    // Arrange: create a board with 3 tiles and a ladder action
    Board board = new Board();
    Tile tile1 = new Tile(1);
    Tile tile2 = new Tile(2);
    Tile tile3 = new Tile(3);
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);
    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);
    LadderAction ladder = new LadderAction(3, "Up to 3");
    tile2.setLandAction(ladder);

    dao.writeBoard(board);
    assertTrue(Files.exists(jsonFile), "JSON file should be created after write");
    Board loaded = dao.readBoard();

    assertEquals(3, loaded.size(), "Loaded board should have 3 tiles");
    Tile loaded1 = loaded.getTile(1);
    Tile loaded2 = loaded.getTile(2);
    Tile loaded3 = loaded.getTile(3);
    assertEquals(2, loaded1.getNextTile().getTileId());
    assertEquals(3, loaded2.getNextTile().getTileId());
    assertNull(loaded3.getNextTile(), "Last tile should have no next tile");
    assertTrue(loaded2.getLandAction() instanceof LadderAction, "Tile 2 should have a "
        + "LadderAction");
    LadderAction loadedAction = (LadderAction) loaded2.getLandAction();
    assertEquals(3, loadedAction.getDestinationTileId());
    assertEquals("Up to 3", loadedAction.getDescription());
  }
}
