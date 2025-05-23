package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.game.*;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

  private GameEngine engine;
  private BoardGame game;
  private Player player1;
  private Player player2;

  @BeforeEach
  void setUp() {
    Board board = new Board(3, 3);
    board.addTile(new Tile(1, 0, 0));
    board.setStartTile(board.getTile(1));

    Dice dice = new Dice(6);
    game = new BoardGame(board, dice);

    player1 = new Player("Alice", new Token("flower", "flower.png"), LocalDate.of(2000, 1, 1));
    player2 = new Player("Bob", new Token("star", "star.png"), LocalDate.of(1999, 5, 5));
    game.addPlayer(player1);
    game.addPlayer(player2);

    engine = new DummyGameEngine(game);
  }

  @Test
  void testGetCurrentAndNextPlayer() {
    assertEquals(player1, engine.getCurrentPlayer());
    engine.nextPlayer();
    assertEquals(player2, engine.getCurrentPlayer());
    engine.nextPlayer();
    assertEquals(player1, engine.getCurrentPlayer());
  }

  @Test
  void testGetLastPlayer() {
    assertEquals(player2, engine.getLastPlayer());
    engine.nextPlayer();
    assertEquals(player1, engine.getLastPlayer());
  }

  @Test
  void testEndGameSetsFlag() {
    assertFalse(engine.isGameOver());
    engine.endGame();
    assertTrue(engine.isGameOver());
  }

  @Test
  void testStartGameNotifiesObservers() {
    DummyObserver observer = new DummyObserver();
    engine.addObserver(observer);
    engine.startGame();
    assertTrue(observer.wasNotified);
    assertEquals(BoardGameEvent.GAME_START, observer.lastEvent);
  }

  @Test
  void testEndGameNotifiesObservers() {
    DummyObserver observer = new DummyObserver();
    engine.addObserver(observer);
    engine.endGame();
    assertTrue(observer.wasNotified);
    assertEquals(BoardGameEvent.GAME_WON, observer.lastEvent);
  }

  @Test
  void testGetPlayersReturnsCorrectList() {
    List<Player> players = engine.getPlayers();
    assertTrue(players.contains(player1));
    assertTrue(players.contains(player2));
  }

  static class DummyGameEngine extends GameEngine {
    public DummyGameEngine(BoardGame game) {
      super(game);
    }

    @Override
    public void playGame() {
    }

    @Override
    public void handleTurn(int total) {

    }

    @Override
    public Player checkWinCondition() {
      return null;
    }
  }

  static class DummyObserver implements BoardGameObserver {
    boolean wasNotified = false;
    BoardGameEvent lastEvent = null;

    @Override
    public void onGameStateChange(BoardGame game, BoardGameEvent event) {
      wasNotified = true;
      lastEvent = event;
    }
  }

  @Test
  void testGetGameAndBoard() {
    assertEquals(game, engine.getGame());
    assertEquals(game.getBoard(), engine.getBoard());
  }
}
