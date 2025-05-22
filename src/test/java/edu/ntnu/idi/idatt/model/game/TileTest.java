package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.action.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

  private Tile tile;

  @BeforeEach
  void setUp() {
    tile = new Tile(1, 0, 0);
  }

  @Test
  void testConstructorStoresValuesCorrectly() {
    assertEquals(1, tile.getTileId());
    assertEquals(0, tile.getRow());
    assertEquals(0, tile.getCol());
    assertNull(tile.getNextTile());
    assertNull(tile.getAction());
  }

  @Test
  void testSetAndGetNextTile() {
    Tile next = new Tile(2, 1, 1);
    tile.setNextTile(next);
    assertEquals(next, tile.getNextTile());
  }

  @Test
  void testSetAndGetRowCol() {
    tile.setRow(5);
    tile.setCol(6);
    assertEquals(5, tile.getRow());
    assertEquals(6, tile.getCol());
  }

  @Test
  void testSetAndGetAction() {
    TileAction dummyAction = new DummyAction();
    tile.setAction(dummyAction);
    assertEquals(dummyAction, tile.getAction());
  }

  @Test
  void testOnLandWithoutActionSetsPlayerTileOnly() {
    Player player = new Player("Test", new Token("leaf", "leaf.png"), LocalDate.of(2001, 2, 2));
    tile.onLand(player);
    assertEquals(tile, player.getCurrentTile());
  }

  @Test
  void testOnLandWithActionCallsPerform() {
    Player player = new Player("Test", new Token("sun", "sun.png"), LocalDate.of(1999, 12, 31));
    TestAction action = new TestAction();
    tile.setAction(action);

    tile.onLand(player);

    assertEquals(tile, player.getCurrentTile());
    assertTrue(action.wasCalled);
  }

  /**
   * Simple TileAction implementation for testing.
   */
  static class TestAction implements TileAction {
    boolean wasCalled = false;

    @Override
    public void perform(Player player) {
      wasCalled = true;
    }
  }

  /**
   * Another dummy class (not used here, but useful if you want different behaviors).
   */
  static class DummyAction implements TileAction {
    @Override
    public void perform(Player player) {
      // do nothing
    }
  }
}
