package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import java.util.List;

/**
 * GameSetup is a configuration class that holds the game information, selected board, and players.
 * It is used to initialize the game with the specified settings.
 *
 *  @author Aminda Lunde
 *  @author Ingrid Opheim
 *  @version 1.0
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
   * @throws GameNotInitializedException if game information is null
   * @throws GameNotInitializedException if selected board is null
   * @throws NoPlayersException if player is null or empty
   */
  public GameSetup(GameInformation gameInformation, Board selectedBoard, List<Player> players) {
    if (gameInformation == null) {
      throw new GameNotInitializedException("Game information cannot be null.");
    }
    if (selectedBoard == null) {
      throw new GameNotInitializedException("Selected board cannot be null.");
    }
    if (players == null || players.isEmpty()) {
      throw new NoPlayersException("Players list cannot be null or empty.");
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
