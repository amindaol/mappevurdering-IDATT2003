package edu.ntnu.idi.idatt.ui;

import edu.ntnu.idi.idatt.config.GameConfiguration;
import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.factory.BoardFactory;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;

import java.util.Comparator;
import java.util.List;

public class GameInitializer {

  public static GameConfiguration create(GameMode mode, List<Player> players) {
    Board board;

    if (mode == GameMode.LOVE_AND_LADDERS) {
      board = BoardFactory.createLoveAndLaddersBoard();
    } else {
      board = BoardFactory.createBestiePointBattlesBoard();
    }

    Dice dice = new Dice(2);
    BoardGame boardGame = new BoardGame(board, dice);
    players.forEach(boardGame::addPlayer);
    boardGame.getPlayers().sort(Comparator.comparing(Player::getBirthday));

    GameEngine engine = switch (mode) {
      case LOVE_AND_LADDERS -> new LoveAndLaddersEngine(board, boardGame.getPlayers(), dice, boardGame.getObservers());
      case BESTIE_POINT_BATTLES -> new BestiePointBattlesEngine(board, boardGame.getPlayers(), dice, boardGame.getObservers());
    };

    return new GameConfiguration(mode, boardGame, engine);
  }
}
