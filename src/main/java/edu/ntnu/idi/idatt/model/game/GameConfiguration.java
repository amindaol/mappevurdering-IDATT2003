package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.game.*;
import java.util.Map;

public class GameConfiguration {

  private final GameMode gameMode;
  private final BoardGame boardGame;
  private final GameEngine gameEngine;

  public GameConfiguration(GameMode gameMode, BoardGame boardGame, GameEngine gameEngine) {
    if (gameMode == null || boardGame == null || gameEngine == null) {
      throw new IllegalArgumentException("GameConfiguration cannot have null fields.");
    }
    this.gameMode = gameMode;
    this.boardGame = boardGame;
    this.gameEngine = gameEngine;
  }

  public GameMode getGameMode() {
    return gameMode;
  }

  public BoardGame getBoardGame() {
    return boardGame;
  }

  public GameEngine getGameEngine() {
    return gameEngine;
  }
}
