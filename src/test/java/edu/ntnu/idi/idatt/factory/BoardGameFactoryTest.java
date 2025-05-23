package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameFactoryTest {

  @Test
  void testCreateLoveAndLaddersGameReturnsCorrectGame() {
    BoardGame game = BoardGameFactory.createLoveAndLaddersGame();
    assertNotNull(game);
    assertEquals(2, game.getPlayers().size());
  }

  @Test
  void testCreateBestiePointBattlesGameReturnsCorrectGame() {
    BoardGame game = BoardGameFactory.createBestiePointBattlesGame();
    assertNotNull(game);
    assertEquals(2, game.getPlayers().size());
  }

  @Test
  void testCreateGameWithModeLoveAndLadders() {
    BoardGame game = BoardGameFactory.createGame(GameMode.LOVE_AND_LADDERS);
    assertNotNull(game);
    assertEquals(2, game.getPlayers().size());
  }

  @Test
  void testCreateGameWithModeBestiePointBattles() {
    BoardGame game = BoardGameFactory.createGame(GameMode.BESTIE_POINT_BATTLES);
    assertNotNull(game);
    assertEquals(2, game.getPlayers().size());
  }

  @Test
  void testCreateLoveAndLaddersGameWithCustomPlayers() {
    Player p1 = new Player("A", TokenFactory.fromIcon("heart"), java.time.LocalDate.of(2000, 1, 1));
    Player p2 = new Player("B", TokenFactory.fromIcon("star"), java.time.LocalDate.of(2000, 1, 2));
    BoardGame game = BoardGameFactory.createLoveAndLaddersGame(List.of(p1, p2));

    assertNotNull(game);
    assertEquals(2, game.getPlayers().size());
    assertTrue(game.getPlayers().contains(p1));
    assertTrue(game.getPlayers().contains(p2));
  }

}
