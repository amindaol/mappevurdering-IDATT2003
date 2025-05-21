package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.config.GameConfiguration;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;

import java.util.List;

/**
 * GameSetup is a configuration class that holds the game information, selected board, and players.
 * It is used to initialize the game with the specified settings.
 */
public class GameSetup {

  private final GameInformation gameInformation;
  private final Board selectedBoard;
  private final List<Player> players;

  /**
   * Constructs a GameSetup with the specified game information, selected board, and players.
   *
   * @param gameInformation the game information
   * @param selectedBoard   the selected board
   * @param players         the list of players
   * @throws IllegalArgumentException if any of the parameters are null or invalid
   */
  public GameSetup(GameInformation gameInformation, Board selectedBoard, List<Player> players) {
    if (gameInformation == null || selectedBoard == null || players == null || players.isEmpty()) {
      throw new IllegalArgumentException("GameSetup requires valid game info, board, and players.");
    }

    this.gameInformation = gameInformation;
    this.selectedBoard = selectedBoard;
    this.players = players;
  }

  /**
   * Builds the game configuration based on the provided game information, selected board, and
   * players.
   *
   * @return a GameConfiguration object containing the game mode, board game, and game engine
   */
  public GameConfiguration build() {
    Dice dice = new Dice(2);
    BoardGame boardGame = new BoardGame(selectedBoard, dice);

    for (Player player : players) {
      boardGame.addPlayer(player);
    }

    GameConfiguration tempConfig = new GameConfiguration(
        gameInformation.getGameMode(),
        boardGame,
        null
    );

    GameEngine engine = gameInformation.getEngineFactory().apply(tempConfig);

    return new GameConfiguration(
        gameInformation.getGameMode(),
        boardGame,
        engine
    );
  }
}
