package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.game.*;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BestiePointBattlesEngineTest {

  private Board board;
  private Dice dice;
  private Player p1, p2;
  private BoardGame game;
  private BestiePointBattlesEngine engine;

  @BeforeEach
  void setUp() {
    board = new Board(2, 2);
    board.addTile(new Tile(1, 0, 0));
    board.addTile(new Tile(2, 0, 1));
    board.setStartTile(board.getTile(1));

    dice = new Dice(1) {
      @Override
      public List<Integer> roll() {
        return List.of(1);
      }
    };

    game = new BoardGame(board, dice);
    p1 = new Player("Aminda", new Token("🌸", "flower.png"), LocalDate.now());
    p2 = new Player("Ingrid", new Token("🌟", "star.png"), LocalDate.now());
    game.addPlayer(p1);
    game.addPlayer(p2);

    engine = new BestiePointBattlesEngine(game, dice);
  }

  @Test
  void testPlayGamePlacesAllPlayersOnStart() {
    engine = new BestiePointBattlesEngine(game, dice) {
      @Override
      public void handleTurn(int total) {
        gameOver = true;
      }
    };
    engine.playGame();

    for (Player p : game.getPlayers()) {
      assertEquals(board.getStartTile(), p.getCurrentTile());
    }
  }

  @Test
  void testHandleTurnSkipsIfPlayerShouldSkip() {
    p1.setSkipNextTurn(true);
    p1.setCurrentTile(board.getTile(1));
    engine.handleTurn(1);
    assertFalse(p1.isSkipNextTurn());
    assertEquals(p1, engine.getLastPlayer());
    assertEquals(p2, engine.getCurrentPlayer());
  }

  @Test
  void testCheckWinConditionReturnsNullIfNotGameOver() {
    assertNull(engine.checkWinCondition());
  }

  @Test
  void testCheckWinConditionReturnsHighestScorePlayer() {
    engine.endGame();
    p1.addPoints(5);
    p2.addPoints(10);
    assertEquals(p2, engine.checkWinCondition());
  }

  @Test
  void testIsFinishedReflectsGameOverState() {
    assertFalse(engine.isFinished());
    engine.endGame();
    assertTrue(engine.isFinished());
  }

  static class DummyObserver implements BoardGameObserver {
    boolean wasNotified = false;

    @Override
    public void onGameStateChange(BoardGame game, BoardGameEvent event) {
      wasNotified = true;
    }
  }
}
