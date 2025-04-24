package edu.ntnu.idi.idatt.game;

import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  private Player player;
  private BoardGame boardGame;
  private Tile startTile;

  @BeforeEach
  void setUp() {
    boardGame = new BoardGame();
    boardGame.createBoard();
    player = new Player("Test Player", boardGame);
    startTile = boardGame.getBoard().getTile(1);
  }

  @Test
  void constructor_setsName() {
    assertEquals("Test Player", player.getName());
  }

  @Test
  void testPlaceOnTile() {
    player.placeOnTile(startTile);
    assertEquals(startTile, player.getCurrentTile());
  }

  @Test
  void movesCorrectNumberOfSteps() {
    player.placeOnTile(startTile);
    startTile.setNextTile(new Tile(2));
    player.move(1);
    assertEquals(2, player.getCurrentTile().getTileId());
  }

  @Test
  void move_withoutTile_throwsException() {
    assertThrows(IllegalStateException.class, () -> player.move(1));
  }

  @Test
  void getBoardGame_returnsCorrectBoardGame() {
    assertEquals(boardGame, player.getBoardGame());
  }
}
