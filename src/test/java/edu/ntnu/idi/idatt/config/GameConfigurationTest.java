package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigurationTest {

  @Test
  void testConstructorAllowsNullEngine() {
    Board board = new Board(1, 1);
    BoardGame boardGame = new BoardGame(board, new Dice(1));

    GameConfiguration config = new GameConfiguration(GameMode.BESTIE_POINT_BATTLES, boardGame,
        null);
    assertNull(config.getEngine());
  }

  @Test
  void testConstructorThrowsOnAllNullValues() {
    assertThrows(GameNotInitializedException.class, () ->
        new GameConfiguration(null, null, null));
  }

  static class DummyEngine extends GameEngine {
    public DummyEngine() {
      super(new BoardGame(new Board(1, 1), new Dice(1)));
    }

    @Override public void playGame() {}
    @Override public void handleTurn(int total) {}
    @Override public Player checkWinCondition() { return null; }
  }

}
