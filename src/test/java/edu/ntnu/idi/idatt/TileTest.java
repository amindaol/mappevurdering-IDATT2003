package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.game.BoardGame;
import edu.ntnu.idi.idatt.game.Player;
import edu.ntnu.idi.idatt.game.Tile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

  private Tile tile;
  private Player player;
  private BoardGame game;

  @BeforeEach
  void setUp() {
    game = new BoardGame();
    tile = new Tile(1);
    player = new Player("Test player", game);
  }

  @Test
  void testConstructorTileId() {
    assertEquals(1, tile.getTileId(), "Tile ID should be set correctly in "
        + "constructor.");
  }

  @Test
  void testSetNextTile() {
    Tile nextTile = new Tile(2);
    tile.setNextTile(nextTile);
    assertEquals(nextTile, tile.getNextTile());
  }

  @Test
  void
}
