package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.model.dao.BoardFileReaderGson;
import edu.ntnu.idi.idatt.model.dao.PlayerFileReaderCsv;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;

import java.nio.file.Path;
import java.util.List;

/**
 * Factory for creating {@link BoardGame} instances, either standard or configured from files.
 */
public final class BoardGameFactory {

  private BoardGameFactory() {
    // prevent instantiation
  }

  /**
   * Creates a standard board game with a 9x10 board and one die, without players.
   * Players must be added separately.
   *
   * @return new BoardGame instance with default board and dice
   */
  public static BoardGame createStandardBoardGame() {
    BoardGame game = new BoardGame();
    game.createBoard();
    game.createDice();
    return game;
  }

  /**
   * Creates a board game configured from JSON and CSV files.
   *
   * @param boardJson  path to JSON file defining board layout and special tiles
   * @param playersCsv path to CSV file listing players (name,token)
   * @return configured BoardGame with board, dice, and players
   * @throws DaoException if reading board or players fails
   */
  public static BoardGame createGameFromConfig(Path boardJson, Path playersCsv) throws DaoException {
    BoardFileReaderGson boardReader = new BoardFileReaderGson();
    var board = boardReader.readBoard(boardJson);

    PlayerFileReaderCsv playerReader = new PlayerFileReaderCsv();
    List<Player> players = playerReader.readPlayers(playersCsv);

    BoardGame game = new BoardGame();
    game.setBoard(board);
    game.createDice();

    // Attach players
    for (Player p : players) {
      p.setBoardGame(game);
      game.addPlayer(p);
    }
    return game;
  }
}
