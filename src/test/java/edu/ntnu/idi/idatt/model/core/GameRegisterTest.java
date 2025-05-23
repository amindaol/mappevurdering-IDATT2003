package edu.ntnu.idi.idatt.model.core;

import edu.ntnu.idi.idatt.config.GameInformation;
import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class GameRegisterTest {

  @Test
  void testRegisterAndGetGame() {
    GameRegister register = new GameRegister();

    GameInformation info = new GameInformation(
        "Test Game",
        "Some rules",
        4,
        2,
        config -> null,
        () -> List.of(),
        GameMode.LOVE_AND_LADDERS
    );

    register.register(info);

    GameInformation retrieved = register.get(GameMode.LOVE_AND_LADDERS);
    assertNotNull(retrieved);
    assertEquals("Test Game", retrieved.getName());
    assertEquals("Some rules", retrieved.getRules());
  }

  @Test
  void testGetAllReturnsAllRegisteredGames() {
    GameRegister register = new GameRegister();

    GameInformation info1 = new GameInformation(
        "Game 1", "Rules 1", 6, 2,
        config -> null, () -> List.of(), GameMode.LOVE_AND_LADDERS
    );

    GameInformation info2 = new GameInformation(
        "Game 2", "Rules 2", 5, 2,
        config -> null, () -> List.of(), GameMode.BESTIE_POINT_BATTLES
    );

    register.register(info1);
    register.register(info2);

    List<GameInformation> all = register.getAll();
    assertEquals(2, all.size());
    assertTrue(all.stream().anyMatch(g -> g.getName().equals("Game 1")));
    assertTrue(all.stream().anyMatch(g -> g.getName().equals("Game 2")));
  }

  @Test
  void testOverwriteExistingGameMode() {
    GameRegister register = new GameRegister();

    GameInformation info1 = new GameInformation(
        "Original", "First rules", 4, 2,
        config -> null, () -> List.of(), GameMode.LOVE_AND_LADDERS
    );

    GameInformation info2 = new GameInformation(
        "Overwritten", "New rules", 5, 3,
        config -> null, () -> List.of(), GameMode.LOVE_AND_LADDERS
    );

    register.register(info1);
    register.register(info2);

    GameInformation current = register.get(GameMode.LOVE_AND_LADDERS);
    assertEquals("Overwritten", current.getName());
    assertEquals("New rules", current.getRules());
  }
}
