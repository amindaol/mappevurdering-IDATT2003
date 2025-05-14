package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.config.GameConfiguration;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;

import java.util.List;

public class GameSetup {

  private final GameInformation gameInformation;
  private final Board selectedBoard;
  private final List<Player> players;

  public GameSetup(GameInformation gameInformation, Board selectedBoard, List<Player> players) {
    if (gameInformation == null || selectedBoard == null || players == null || players.isEmpty()) {
      throw new IllegalArgumentException("GameSetup requires valid game info, board, and players.");
    }

    this.gameInformation = gameInformation;
    this.selectedBoard = selectedBoard;
    this.players = players;
  }

  private GameConfiguration build() {
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
