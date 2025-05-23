package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.TooManyPlayersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

  private BoardGame game;
  private Board board;
  private Dice dice;

  @BeforeEach
  void setUp() {
    board = new Board(3, 3);
    dice = new Dice(6);
    game = new BoardGame(board, dice);
  }

  @Test
  void testConstructorStoresBoardAndDice() {
    assertEquals(board, game.getBoard());
    assertEquals(dice, game.getDice());
  }

  @Test
  void testConstructorThrowsIfNull() {
    assertThrows(NullPointerException.class, () -> new BoardGame(null, dice));
    assertThrows(NullPointerException.class, () -> new BoardGame(board, null));
  }

  @Test
  void testAddPlayerAddsCorrectly() {
    Player player = new Player("Alice", new Token("star", "star.png"), LocalDate.now());
    game.addPlayer(player);
    assertTrue(game.getPlayers().contains(player));
  }

  @Test
  void testAddPlayerThrowsIfNull() {
    assertThrows(NullPointerException.class, () -> game.addPlayer(null));
  }

  @Test
  void testAddPlayerThrowsIfTooMany() {
    for (int i = 0; i < BoardGame.MAX_PLAYERS; i++) {
      game.addPlayer(new Player("P" + i, new Token("t", "t.png"), LocalDate.now()));
    }
    assertThrows(TooManyPlayersException.class, () ->
        game.addPlayer(new Player("Extra", new Token("x", "x.png"), LocalDate.now())));
  }

  @Test
  void testAddAndRemoveObserver() {
    DummyObserver observer = new DummyObserver();
    game.addObserver(observer);
    assertTrue(game.getObservers().contains(observer));
    game.removeObserver(observer);
    assertFalse(game.getObservers().contains(observer));
  }

  @Test
  void testClearObserversRemovesAll() {
    game.addObserver(new DummyObserver());
    game.clearObservers();
    assertTrue(game.getObservers().isEmpty());
  }

  @Test
  void testGetBoardThrowsIfNull() {
    BoardGame broken = new BoardGame(board, dice);
  }

  // === Dummy Observer ===
  static class DummyObserver implements BoardGameObserver {
    boolean wasNotified = false;

    @Override
    public void onGameStateChange(BoardGame game, BoardGameEvent event) {
      wasNotified = true;
    }
  }

  @Test
  void testNotifyObserversFiresEvent() {
    DummyObserver observer = new DummyObserver();
    game.addObserver(observer);
    game.notifyObservers(BoardGameEvent.GAME_START);

    assertTrue(observer.wasNotified);
  }
}
