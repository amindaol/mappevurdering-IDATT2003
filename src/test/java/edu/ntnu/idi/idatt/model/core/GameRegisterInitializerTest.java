package edu.ntnu.idi.idatt.model.core;

import edu.ntnu.idi.idatt.config.GameInformation;
import edu.ntnu.idi.idatt.config.GameMode;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameRegisterInitializerTest {

  private GameRegister register;

  @BeforeEach
  void setUp() {
    register = new GameRegister();
    GameRegisterInitializer.initialize(register);
  }

  @Test
  void testCorrectNumberOfGamesRegistered() {
    List<GameInformation> allGames = register.getAll();
    assertEquals(2, allGames.size());
  }

  @Test
  void testLoveAndLaddersIsRegistered() {
    GameInformation info = register.get(GameMode.LOVE_AND_LADDERS);
    assertNotNull(info);
    assertEquals("Love & Ladders", info.getName());
    assertEquals("Classic snakes and ladders. First player to tile 90 wins. Some tiles have special actions.",
        info.getRules());
    assertEquals(6, info.getPlayerMax());
    assertEquals(2, info.getPlayerMin());
  }


  @Test
  void testBestiePointBattlesIsRegistered() {
    GameInformation info = register.get(GameMode.BESTIE_POINT_BATTLES);
    assertNotNull(info);
    assertEquals("Bestie Point Battles", info.getName());
    assertTrue(info.getRules().contains("Collect the most points"));
  }
}
