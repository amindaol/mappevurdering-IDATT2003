package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.game.action.LadderAction;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LadderActionTest {

  @Test
  void constructor_setsDestinationTileId() {
    LadderAction action = new LadderAction(5, "Climb to tile 5");
    assertEquals(5, action.getDestinationTileId());
  }

  @Test
  void constructor_setsDescription() {
    LadderAction action = new LadderAction(5, "Climb to tile 5");
    assertEquals("Climb to tile 5", action.getDescription());
  }

  @Test
  void perform_movesPlayer() {
    BoardGame game = new BoardGame(board, dice);
    game.createBoard();
    Player player = new Player("Test Player", game, LocalDate.of(2000, 1, 1));

    LadderAction action = new LadderAction(5, "Climb to tile 5");
    action.perform(player);

    assertEquals(5, player.getCurrentTile().getTileId());
  }

  @Test
  void invalidDestinationTileId_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> new LadderAction(0,
        "Invalid"));
  }

  @Test
  void nullDescription_throwsException() {
    assertThrows(NullPointerException.class, () -> new LadderAction(5,
        null));
  }


}
