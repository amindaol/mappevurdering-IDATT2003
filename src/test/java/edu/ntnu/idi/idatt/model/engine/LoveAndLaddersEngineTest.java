package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.game.*;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoveAndLaddersEngineTest {

  private Board board;
  private Dice dice;
  private Player p1, p2;
  private BoardGame game;
  private LoveAndLaddersEngine engine;

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

    engine = new LoveAndLaddersEngine(game, dice);
  }

  @Test
  void testStartGamePlacesAllPlayersOnStartTile() {
    engine.startGame();
    assertEquals(board.getStartTile(), p1.getCurrentTile());
    assertEquals(board.getStartTile(), p2.getCurrentTile());
  }

  @Test
  void testCheckWinConditionReturnsNullIfNobodyOnLastTile() {
    p1.setCurrentTile(board.getTile(1));
    assertNull(engine.checkWinCondition());
  }

  @Test
  void testCheckWinConditionReturnsPlayerOnLastTile() {
    p1.setCurrentTile(board.getTile(2));
    assertEquals(p1, engine.checkWinCondition());
  }

  @Test
  void testObserversAreNotifiedAtStartAndEnd() {
    DummyObserver observer = new DummyObserver();
    engine.addObserver(observer);

    p1.setCurrentTile(board.getTile(1));
    p2.setCurrentTile(board.getTile(1));

    engine = new LoveAndLaddersEngine(game, dice) {
      @Override
      public void handleTurn(int steps) {
        gameOver = true;
      }
    };
    engine.addObserver(observer);
    engine.playGame();

    assertTrue(observer.wasNotified);
  }

  static class DummyObserver implements BoardGameObserver {
    boolean wasNotified = false;

    @Override
    public void onGameStateChange(BoardGame game, BoardGameEvent event) {
      wasNotified = true;
    }
  }

  @Test
  void testHandleTurnSkipsIfPlayerHasSkipFlag() {
    p1.setSkipNextTurn(true);
    Tile t1 = board.getTile(1);
    Tile t2 = new Tile(2, 1, 1);
    board.addTile(t2);
    t1.setNextTile(t2);

    engine.startGame();
    engine.handleTurn(1);

    assertEquals(t1, p1.getCurrentTile());
  }
}
