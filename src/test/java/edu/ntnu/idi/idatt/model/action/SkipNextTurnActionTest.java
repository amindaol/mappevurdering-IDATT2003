package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SkipNextTurnActionTest {

  @Test
  void testPerformSetsSkipNextTurnTrue() {
    Player player = new Player("Aminda", new Token("cat", "cat.png"), LocalDate.of(2000, 1, 1));
    assertFalse(player.isSkipNextTurn());

    SkipNextTurnAction action = new SkipNextTurnAction();
    action.perform(player);

    assertTrue(player.isSkipNextTurn());
  }

  @Test
  void testPerformThrowsIfPlayerIsNull() {
    SkipNextTurnAction action = new SkipNextTurnAction();
    assertThrows(NullPointerException.class, () -> action.perform(null));
  }
}
