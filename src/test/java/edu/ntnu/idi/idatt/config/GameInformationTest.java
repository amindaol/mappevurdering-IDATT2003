package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.config.GameSetupTest.DummyEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameInformationTest {

  @Test
  void testConstructorStoresAllFieldsCorrectly() {
    Function<GameConfiguration, GameEngine> engineFactory = config -> new DummyEngine();
    Board dummyBoard = new Board(1, 1);

    GameInformation info = new GameInformation(
        "Test Game",
        "Simple test rules",
        4,
        2,
        engineFactory,
        () -> List.of(dummyBoard),
        GameMode.LOVE_AND_LADDERS
    );

    assertEquals("Test Game", info.getName());
    assertEquals("Simple test rules", info.getRules());
    assertEquals(4, info.getPlayerMax());
    assertEquals(2, info.getPlayerMin());
    assertEquals(GameMode.LOVE_AND_LADDERS, info.getGameMode());
    assertEquals(dummyBoard, info.getBoardOptions().get(0));
  }

  @Test
  void testToStringIncludesNameAndPlayerRange() {
    GameInformation info = new GameInformation(
        "Test",
        "rules",
        4,
        2,
        config -> null,
        () -> Collections.emptyList(),
        GameMode.BESTIE_POINT_BATTLES
    );
    assertTrue(info.toString().contains("Test"));
    assertTrue(info.toString().contains("2–4"));
  }

  @Test
  void testConstructorThrowsOnNullArguments() {
    assertThrows(IllegalArgumentException.class, () -> new GameInformation(
        null, "rules", 4, 2, config -> null, () -> List.of(), GameMode.LOVE_AND_LADDERS
    ));

    assertThrows(IllegalArgumentException.class, () -> new GameInformation(
        "Test", "rules", 4, 2, null, () -> List.of(), GameMode.LOVE_AND_LADDERS
    ));

    assertThrows(IllegalArgumentException.class, () -> new GameInformation(
        "Test", "rules", 4, 2, config -> null, null, GameMode.LOVE_AND_LADDERS
    ));
  }
}
