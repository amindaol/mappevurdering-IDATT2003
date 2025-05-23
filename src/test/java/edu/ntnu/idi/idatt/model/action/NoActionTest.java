package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NoActionTest {

  @Test
  void testPerformDoesNothingOnValidPlayer() {
    Player player = new Player("Test", new Token("cat", "cat.png"), LocalDate.of(2000, 1, 1));
    player.setSkipNextTurn(true);
    int pointsBefore = player.getPoints();

    NoAction action = new NoAction();
    action.perform(player); // skal ikke endre noe

    assertTrue(player.isSkipNextTurn()); // fortsatt true
    assertEquals(pointsBefore, player.getPoints()); // fortsatt likt
  }

  @Test
  void testPerformDoesNothingOnNullPlayer() {
    NoAction action = new NoAction();
    assertDoesNotThrow(() -> action.perform(null)); // bør være helt trygt
  }
}
