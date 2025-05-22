package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;

import edu.ntnu.idi.idatt.model.game.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameSetupTest {

  @Test
  void testConstructorThrowsOnInvalidArguments() {
    Board board = new Board(2, 2);
    Player player = new Player("A", new Token("cat", "cat.png"), LocalDate.now());

    GameInformation info = new GameInformation(
        "Test Game", "Test rules", 4, 2,
        config -> null, () -> List.of(board), GameMode.LOVE_AND_LADDERS
    );

    assertThrows(IllegalArgumentException.class, () -> new GameSetup(null, board, List.of(player)));
    assertThrows(IllegalArgumentException.class, () -> new GameSetup(info, null, List.of(player)));
    assertThrows(IllegalArgumentException.class, () -> new GameSetup(info, board, null));
    assertThrows(IllegalArgumentException.class, () -> new GameSetup(info, board, List.of()));
  }

  @Test
  void testBuildReturnsGameConfigurationWithEngineAndBoardGame() {
    Board board = new Board(2, 2);
    Player player1 = new Player("Aminda", new Token("🌸", "flower.png"), LocalDate.of(2000, 1, 1));
    Player player2 = new Player("Ingrid", new Token("🌟", "star.png"), LocalDate.of(1999, 5, 5));

    DummyEngine dummyEngine = new DummyEngine();

    GameInformation info = new GameInformation(
        "Test Game",
        "A test game with dummy engine",
        4,
        2,
        ignored -> dummyEngine,
        () -> List.of(board),
        GameMode.BESTIE_POINT_BATTLES
    );

    GameSetup setup = new GameSetup(info, board, List.of(player1, player2));
    GameConfiguration config = setup.build();

    assertNotNull(config);
    assertEquals(GameMode.BESTIE_POINT_BATTLES, config.getGameMode());
    assertEquals(dummyEngine, config.getEngine());

    BoardGame boardGame = config.getBoardGame();
    assertNotNull(boardGame);
    assertEquals(2, boardGame.getPlayers().size());
    assertTrue(boardGame.getPlayers().contains(player1));
    assertTrue(boardGame.getPlayers().contains(player2));
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
