package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigurationTest {

  @Test
  void testConstructorStoresValuesCorrectly() {
    Board board = new Board(1, 1);
    BoardGame boardGame = new BoardGame(board, new Dice(1));
    GameEngine engine = new DummyEngine();

    GameConfiguration config = new GameConfiguration(GameMode.LOVE_AND_LADDERS, boardGame, engine);

    assertEquals(GameMode.LOVE_AND_LADDERS, config.getGameMode());
    assertEquals(boardGame, config.getBoardGame());
    assertEquals(engine, config.getEngine());
  }

  @Test
  void testConstructorAllowsNullEngine() {
    Board board = new Board(1, 1);
    BoardGame boardGame = new BoardGame(board, new Dice(1));

    GameConfiguration config = new GameConfiguration(GameMode.BESTIE_POINT_BATTLES, boardGame, null);
    assertNull(config.getEngine());
  }

  @Test
  void testConstructorThrowsOnNullBoardOrGameMode() {
    Board board = new Board(1, 1);
    BoardGame boardGame = new BoardGame(board, new Dice(1));
    GameEngine engine = new DummyEngine();

    assertThrows(IllegalArgumentException.class, () -> new GameConfiguration(null, boardGame, engine));
    assertThrows(IllegalArgumentException.class, () -> new GameConfiguration(GameMode.BESTIE_POINT_BATTLES, null, engine));
  }

  static class DummyEngine extends GameEngine {
    public DummyEngine() {
      super(new BoardGame(new Board(1, 1), new Dice(1)));
    }

    @Override public void playGame() {}
    @Override public void handleTurn(int total) {}
    @Override public Player checkWinCondition() { return null; }
  }

  @Test
  void testConstructorThrowsOnNullGameModeAndBoardGame() {
    GameEngine engine = new DummyEngine();

    assertThrows(IllegalArgumentException.class, () ->
        new GameConfiguration(null, null, engine));
  }

  @Test
  void testConstructorThrowsOnAllNullValues() {
    assertThrows(IllegalArgumentException.class, () ->
        new GameConfiguration(null, null, null));
  }

}
