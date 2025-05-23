package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.model.action.JumpToTileAction;
import edu.ntnu.idi.idatt.model.action.ModifyPointsAction;
import edu.ntnu.idi.idatt.model.action.SkipNextTurnAction;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

  @Test
  void testCreateDefaultBoardStructure() {
    Board board = BoardFactory.createDefaultBoard();

    assertNotNull(board);
    assertEquals(90, board.getTiles().size());

    Tile first = board.getTile(1);
    Tile second = board.getTile(2);
    assertSame(second, first.getNextTile());

    assertEquals(first, board.getStartTile());
  }

  @Test
  void testCreateLoveAndLaddersBoardActions() {
    Board board = BoardFactory.createLoveAndLaddersBoard();

    assertNotNull(board.getTile(3).getAction());
    assertNotNull(board.getTile(8).getAction());
    assertNotNull(board.getTile(19).getAction());
    assertNotNull(board.getTile(25).getAction());

    assertEquals(14, ((JumpToTileAction) board.getTile(3).getAction()).getDestination().getTileId());
    assertEquals(4, ((JumpToTileAction) board.getTile(8).getAction()).getDestination().getTileId());

  }

  @Test
  void testCreateBestiePointBattlesBoardActions() {
    Board board = BoardFactory.createBestiePointBattlesBoard();

    assertTrue(board.getTile(5).getAction() instanceof ModifyPointsAction);
    assertEquals(3, ((ModifyPointsAction) board.getTile(5).getAction()).getPoints());

    assertTrue(board.getTile(10).getAction() instanceof ModifyPointsAction);
    assertEquals(-2, ((ModifyPointsAction) board.getTile(10).getAction()).getPoints());

    assertTrue(board.getTile(15).getAction() instanceof ModifyPointsAction);
    assertEquals(5, ((ModifyPointsAction) board.getTile(15).getAction()).getPoints());

    assertTrue(board.getTile(20).getAction() instanceof SkipNextTurnAction);
  }

  @Test
  void testTilesWithoutActionsRemainNull() {
    Board board = BoardFactory.createBestiePointBattlesBoard();
    assertNull(board.getTile(6).getAction());
  }

}
