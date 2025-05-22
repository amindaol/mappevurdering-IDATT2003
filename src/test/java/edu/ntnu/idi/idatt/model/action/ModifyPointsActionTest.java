package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ModifyPointsActionTest {

  @Test
  void testPerformAddsPoints() {
    Player player = new Player("Aminda", new Token("cat", "cat.png"), LocalDate.of(2000, 1, 1));
    ModifyPointsAction action = new ModifyPointsAction(10);

    action.perform(player);

    assertEquals(10, player.getPoints());
  }

  @Test
  void testPerformSubtractsPoints() {
    Player player = new Player("Ingrid", new Token("star", "star.png"), LocalDate.of(2000, 1, 1));
    ModifyPointsAction action = new ModifyPointsAction(-5);

    action.perform(player);

    assertEquals(-5, player.getPoints());
  }

  @Test
  void testPerformWithZeroPoints() {
    Player player = new Player("Neutral", new Token("moon", "moon.png"), LocalDate.of(2000, 1, 1));
    ModifyPointsAction action = new ModifyPointsAction(0);

    action.perform(player);

    assertEquals(0, player.getPoints());
  }

  @Test
  void testGetPointsReturnsCorrectValue() {
    ModifyPointsAction action = new ModifyPointsAction(42);
    assertEquals(42, action.getPoints());
  }

  @Test
  void testPerformThrowsIfPlayerIsNull() {
    ModifyPointsAction action = new ModifyPointsAction(5);
    assertThrows(NullPointerException.class, () -> action.perform(null));
  }
}
