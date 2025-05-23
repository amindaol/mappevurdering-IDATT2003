package edu.ntnu.idi.idatt.model.core;

import edu.ntnu.idi.idatt.model.game.*;
import edu.ntnu.idi.idatt.model.action.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LinearMovementTest {

  private LinearMovement movement;
  private Board board;
  private Player player;

  @BeforeEach
  void setUp() {
    movement = new LinearMovement();

    board = new Board(1, 5);
    Tile t1 = new Tile(1, 0, 0);
    Tile t2 = new Tile(2, 0, 1);
    Tile t3 = new Tile(3, 0, 2);
    Tile t4 = new Tile(4, 0, 3);
    Tile t5 = new Tile(5, 0, 4);

    t1.setNextTile(t2);
    t2.setNextTile(t3);
    t3.setNextTile(t4);
    t4.setNextTile(t5);

    board.addTile(t1);
    board.addTile(t2);
    board.addTile(t3);
    board.addTile(t4);
    board.addTile(t5);
    board.setStartTile(t1);

    player = new Player("Test", new Token("cat", "cat.png"), LocalDate.of(2000, 1, 1));
    player.setCurrentTile(t1);
  }

  @Test
  void testMoveOneStep() {
    movement.move(player, 1);
    assertEquals(2, player.getCurrentTile().getTileId());
  }

  @Test
  void testMoveMultipleSteps() {
    movement.move(player, 3);
    assertEquals(4, player.getCurrentTile().getTileId());
  }

  @Test
  void testMoveStopsAtEndOfBoard() {
    movement.move(player, 10);
    assertEquals(5, player.getCurrentTile().getTileId());
  }

  @Test
  void testMoveThrowsIfCurrentTileIsNull() {
    player.setCurrentTile(null);
    assertThrows(IllegalStateException.class, () -> movement.move(player, 1));
  }

  @Test
  void testMoveTriggersTileActionIfPresent() {
    TileActionMock action = new TileActionMock();
    board.getTile(2).setAction(action);

    movement.move(player, 1);
    assertTrue(action.wasCalled);
  }

  static class TileActionMock implements TileAction {
    boolean wasCalled = false;

    @Override
    public void perform(Player player) {
      wasCalled = true;
    }
  }
}
