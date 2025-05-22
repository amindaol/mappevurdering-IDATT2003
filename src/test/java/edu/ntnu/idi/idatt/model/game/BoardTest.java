package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  private Board board;
  private Tile tile1;
  private Tile tile2;

  @BeforeEach
  void setUp() {
    board = new Board(3, 3);
    tile1 = new Tile(1, 0, 0);
    tile2 = new Tile(2, 0, 1);
  }

  @Test
  void testAddAndGetTile() {
    board.addTile(tile1);
    assertEquals(tile1, board.getTile(1));
  }

  @Test
  void testAddTileThrowsIfNull() {
    assertThrows(NullPointerException.class, () -> board.addTile(null));
  }

  @Test
  void testGetTileThrowsIfNotFound() {
    assertThrows(InvalidMoveException.class, () -> board.getTile(99));
  }

  @Test
  void testSizeReflectsAddedTiles() {
    board.addTile(tile1);
    board.addTile(tile2);
    assertEquals(2, board.size());
  }

  @Test
  void testGetLastTileReturnsHighestIdTile() {
    Tile tile3 = new Tile(5, 1, 1);
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);
    assertEquals(tile3, board.getLastTile());
  }

  @Test
  void testGetLastTileThrowsIfEmpty() {
    assertThrows(InvalidMoveException.class, () -> board.getLastTile());
  }

  @Test
  void testGetTilesReturnsAll() {
    board.addTile(tile1);
    board.addTile(tile2);
    List<Tile> tiles = board.getTiles();
    assertTrue(tiles.contains(tile1));
    assertTrue(tiles.contains(tile2));
  }

  @Test
  void testGetTilesOrderedReturnsInCorrectOrder() {
    Tile tileA = new Tile(10, 1, 1);
    Tile tileB = new Tile(2, 0, 1);
    board.addTile(tileA);
    board.addTile(tileB);
    List<Tile> ordered = board.getTilesOrdered();
    assertEquals(2, ordered.get(0).getTileId());
    assertEquals(10, ordered.get(1).getTileId());
  }

  @Test
  void testStartTileSetterAndGetter() {
    board.setStartTile(tile1);
    assertEquals(tile1, board.getStartTile());
  }

  @Test
  void testStartTileThrowsIfNotSet() {
    assertThrows(InvalidMoveException.class, () -> board.getStartTile());
  }

  @Test
  void testSetStartTileThrowsIfNull() {
    assertThrows(NullPointerException.class, () -> board.setStartTile(null));
  }

  @Test
  void testGetAndSetLadders() {
    Ladder ladder = new Ladder(1, 5);
    board.setLadders(List.of(ladder));
    assertEquals(1, board.getLadders().size());
    assertEquals(ladder, board.getLadders().get(0));
  }

  @Test
  void testGetLadderFromTileReturnsCorrectLadder() {
    Ladder ladder = new Ladder(2, 8);
    board.setLadders(List.of(ladder));
    assertEquals(ladder, board.getLadderFromTile(2));
  }

  @Test
  void testGetLadderFromTileReturnsNullIfNotFound() {
    Ladder ladder = new Ladder(1, 9);
    board.setLadders(List.of(ladder));
    assertNull(board.getLadderFromTile(99));
  }

  @Test
  void testGetAndSetSnakes() {
    Ladder snake = new Ladder(10, 3); // same class used for snakes
    board.setSnakes(List.of(snake));
    assertEquals(1, board.getSnakes().size());
    assertEquals(snake, board.getSnakes().get(0));
  }

  @Test
  void testGetBoardDimensions() {
    assertEquals(3, board.getRows());
    assertEquals(3, board.getCols());
  }
}
