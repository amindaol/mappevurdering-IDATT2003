package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.model.game.Token;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JumpToTileActionTest {

  @Test
  void testPerformMovesPlayerToDestinationTile() {
    Tile destination = new Tile(10, 1, 1);
    Player player = new Player("Aminda", new Token("flower", "flower.png"), LocalDate.of(2000, 1, 1));
    JumpToTileAction action = new JumpToTileAction(destination);

    action.perform(player);

    assertEquals(destination, player.getCurrentTile());
  }

  @Test
  void testGetDestinationReturnsCorrectTile() {
    Tile destination = new Tile(5, 0, 0);
    JumpToTileAction action = new JumpToTileAction(destination);
    assertEquals(destination, action.getDestination());
  }

  @Test
  void testConstructorThrowsIfDestinationIsNull() {
    assertThrows(NullPointerException.class, () -> new JumpToTileAction(null));
  }

  @Test
  void testPerformHandlesNullPlayerGracefully() {
    Tile destination = new Tile(8, 2, 2);
    JumpToTileAction action = new JumpToTileAction(destination);

    assertThrows(NullPointerException.class, () -> action.perform(null));
  }
}
