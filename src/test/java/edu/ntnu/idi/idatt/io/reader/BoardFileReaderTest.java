package edu.ntnu.idi.idatt.io.reader;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class BoardFileReaderTest {

  private final Path validPath;

  {
    try {
      validPath = Path.of(getClass().getResource("/boards/test-board.json").toURI());
    } catch (Exception e) {
      throw new RuntimeException("Failed to resolve test-board.json", e);
    }
  }

  private final Path invalidPath = Path.of("src/test/resources/boards/nonexistent.json");
  private final BoardFileReaderGson reader = new BoardFileReaderGson();

  @Test
  void testReadBoardLoadsCorrectStructure() {
    Board board = reader.readBoard(validPath);

    assertNotNull(board);
    assertEquals(2, board.getRows());
    assertEquals(2, board.getCols());
    assertEquals(4, board.getTiles().size());

    Tile start = board.getStartTile();
    assertNotNull(start);
    assertEquals(1, start.getTileId());

    assertEquals(2, board.getTile(1).getNextTile().getTileId());
    assertEquals(3, board.getTile(2).getNextTile().getTileId());
  }

  @Test
  void testReadBoardThrowsOnMissingFile() {
    assertThrows(DaoException.class, () -> reader.readBoard(invalidPath));
  }

  @Test
  void testParseBoardThrowsOnUnknownType() {
    String json = """
        {
          "rows": 1,
          "cols": 1,
          "tiles": [{"id": 1, "row": 0, "col": 0}],
          "specialTiles": [{
            "id": 1,
            "action": { "type": "UnknownAction" }
          }]
        }
        """;

    assertThrows(IllegalArgumentException.class, () ->
        reader.parseBoard(com.google.gson.JsonParser.parseString(json).getAsJsonObject()));
  }
}
