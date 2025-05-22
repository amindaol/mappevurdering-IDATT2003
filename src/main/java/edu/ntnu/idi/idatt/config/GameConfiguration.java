package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.*;

/**
 * GameConfiguration is a configuration class that holds the game mode, board game, and game engine.
 * It is used to initialize the game with the specified settings.
 *
 *  @author Aminda Lunde
 *  @author Ingrid Opheim
 *  @version 1.0
 */
public class GameConfiguration {

  private final GameMode gameMode;
  private final BoardGame boardGame;
  private final GameEngine gameEngine;

  /**
   * Constructs a GameConfiguration with the specified game mode, board game, and game engine.
   *
   * @param gameMode   the game mode
   * @param boardGame  the board game
   * @param gameEngine the game engine
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public GameConfiguration(GameMode gameMode, BoardGame boardGame, GameEngine gameEngine) {
    if (gameMode == null || boardGame == null || gameEngine == null) {
      throw new IllegalArgumentException("GameConfiguration cannot have null fields.");
    }
    this.gameMode = gameMode;
    this.boardGame = boardGame;
    this.gameEngine = gameEngine;
  }

  /**
   * Returns the game mode of this configuration.
   *
   * @return the game mode
   */
  public GameMode getGameMode() {
    return gameMode;
  }

  /**
   * Returns the board game of this configuration.
   *
   * @return the board game
   */
  public BoardGame getBoardGame() {
    return boardGame;
  }

  /**
   * Returns the game engine of this configuration.
   *
   * @return the game engine
   */
  public GameEngine getGameEngine() {
    return gameEngine;
  }
}
