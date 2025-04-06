package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.game.Board;
import edu.ntnu.idi.idatt.game.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  @Test
  void addTile_addsTileSuccessfully() {
    Board board = new Board();
    Tile tile = new Tile(1);
    board.addTile(tile);
    assertEquals(tile, board.getTile(1));
  }

  @Test
  void getTile_returnsCorrectTile() {
    Board board = new Board();
    Tile tile = new Tile(5);
    board.addTile(tile);
    assertEquals(tile, board.getTile(5));
  }

  @Test
  void size_returnsCorrectNumberOfTiles() {
    Board board = new Board();
    board.addTile(new Tile(1));
    board.addTile(new Tile(2));
    assertEquals(2, board.size());
  }

  @Test
  void addTile_nullTile_throwsException() {
    Board board = new Board();
    assertThrows(NullPointerException.class, () -> board.addTile(null));
  }

}
