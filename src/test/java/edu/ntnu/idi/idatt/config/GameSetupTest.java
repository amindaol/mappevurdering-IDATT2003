package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;

import edu.ntnu.idi.idatt.model.game.*;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameSetupTest {

  @Test
  void testConstructorThrowsOnInvalidArguments() {
    Board board = new Board(2, 2);
    Player player = new Player("A", new Token("cat", "cat.png"), LocalDate.now());

    GameInformation gameInformation = new GameInformation(
        "Test Game", "Test rules", 4, 2,
        config -> null, () -> List.of(board), GameMode.LOVE_AND_LADDERS
    );

    assertThrows(GameNotInitializedException.class, () -> new GameSetup(null, board, List.of(player)));
    assertThrows(GameNotInitializedException.class, () -> new GameSetup(gameInformation, null, List.of(player)));
    assertThrows(NoPlayersException.class, () -> new GameSetup(gameInformation, board, null));
    assertThrows(NoPlayersException.class, () -> new GameSetup(gameInformation, board, List.of()));
  }

  @Test
  void testConstructorAllowsNullEngine() {
    Board board = new Board(2, 2);
    Player player = new Player("Aminda", new Token("🌸", "flower.png"), LocalDate.of(2000, 1, 1));

    GameInformation info = new GameInformation(
        "Test Game", "Test rules", 4, 2,
        config -> null, () -> List.of(board), GameMode.LOVE_AND_LADDERS
    );

    GameSetup setup = new GameSetup(info, board, List.of(player));
    GameConfiguration config = setup.build();

    assertNotNull(config);
    assertNull(config.getEngine());  // Ensure engine is null when not provided
  }

  static class DummyEngine extends GameEngine {

    public DummyEngine() {
      super(new BoardGame(new Board(1, 1), new Dice(1)));
    }
    @Override
    public void playGame() {}
    @Override
    public void handleTurn(int total) {}
    @Override
    public Player checkWinCondition() { return null; }
    @Override
    public void addObserver(BoardGameObserver observer) {}
    @Override
    public void notifyObservers(BoardGameEvent event) {}
    @Override
    public void endGame() {}
    @Override
    public Player getCurrentPlayer() { return null; }
    @Override
    public Player getLastPlayer() { return null; }
    @Override
    public void nextPlayer() {}
    @Override
    public Board getBoard() { return null; }
    @Override
    public List<Player> getPlayers() { return null; }
    @Override
    public boolean isGameOver() { return false; }
    @Override
    public BoardGame getGame() { return null; }
    @Override
    public void startGame() {}
  }

}
