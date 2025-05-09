package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.model.dao.BoardFileReaderGson;
import edu.ntnu.idi.idatt.model.dao.PlayerFileReaderCsv;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.GameMode;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.PointBoardGame;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;

import java.io.InputStream;
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
    Board board = new Board();
    for (int i = 1; i <= 30; i++) {
      board.addTile(new Tile(i));
    }
    for (int i = 1; i < 90; i++) {
      board.getTile(i).setNextTile(board.getTile(i + 1));
    }
    Dice dice = new Dice(1);
    return new BoardGame(board, dice);
  }

  /**
   * Creates a board game configured from JSON and CSV files.
   *
   * @param boardJson  path to JSON file defining board layout and special tiles
   * @param playersCsv path to CSV file listing players (name,token)
   * @return configured BoardGame with board, dice, and players
   * @throws DaoException if reading board or players fails
   */
  public static BoardGame createGameFromConfig(InputStream boardJson, Path playersCsv) throws DaoException {
    BoardFileReaderGson boardReader = new BoardFileReaderGson();
    var board = boardReader.readBoard(boardJson);

    PlayerFileReaderCsv playerReader = new PlayerFileReaderCsv();
    List<Player> players = playerReader.readPlayers(playersCsv);

    Dice dice = new Dice(1);
    BoardGame game = new BoardGame(board, dice);
    for (Player p : players) {
      p.setBoardGame(game);
      game.addPlayer(p);
    }
    return game;
  }

  public static BoardGame createGame(GameMode mode) {
    Board board = loadBoardFromJson(mode);
    Dice dice = new Dice(2);

    return switch (mode) {
      case LOVE_AND_LADDERS -> new BoardGame(board, dice);
      case BESTIE_POINT_BATTLES -> new PointBoardGame(board, dice);
    };
  }

  private static Board loadBoardFromJson(GameMode mode) {
    String fileName = switch (mode) {
      case LOVE_AND_LADDERS -> "ladderBoard1.json";
      case BESTIE_POINT_BATTLES -> "pointBoard1.json";
    };

    try (InputStream stream = BoardGameFactory.class.getClassLoader().getResourceAsStream("boards/" + fileName)) {
      if (stream == null) {
        throw new RuntimeException("Could not find board file: " + fileName);
      }
      return new BoardFileReaderGson().readBoard(stream);
    } catch (Exception e) {
      throw new RuntimeException("Failed to load board file: " + fileName, e);
    }
  }
}
