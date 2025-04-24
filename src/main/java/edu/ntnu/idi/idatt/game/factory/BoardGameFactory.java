package edu.ntnu.idi.idatt.game.factory;

import edu.ntnu.idi.idatt.dao.BoardFileReaderGson;
import edu.ntnu.idi.idatt.dao.PlayerFileReader;
import edu.ntnu.idi.idatt.dao.PlayerFileReaderCsv;
import edu.ntnu.idi.idatt.game.Board;
import edu.ntnu.idi.idatt.game.BoardGame;
import edu.ntnu.idi.idatt.game.Player;
import java.nio.file.Path;
import java.util.List;

/**
 * Factory for creating configured {@link BoardGame} instances.
 */
public final class BoardGameFactory {

  private BoardGameFactory() {
  }

  /**
   * Creates a standard game with a hard-coded 30-tile board and one die.
   */
  public static BoardGame createStandardBoardGame() {
    BoardGame game = new BoardGame();
    game.createBoard();
    game.createDice();
    return game;
  }

  /**
   * Creates a game by loading board configuration from JSON and player list from CSV.
   *
   * @param boardJson  path to the JSON file defining the board
   * @param playersCsv path to the CSV file defining players
   * @return a configured {@link BoardGame} instance
   */
  public static BoardGame createGameFromConfig(Path boardJson, Path playersCsv) {
    BoardGame game = new BoardGame();

    BoardFileReaderGson boardReader = new BoardFileReaderGson();
    Board board = boardReader.readBoard(boardJson);
    game.setBoard(board);

    PlayerFileReader playerReader = new PlayerFileReaderCsv();
    List<Player> players = playerReader.readPlayers(playersCsv);
    game.getPlayers().clear();
    game.getPlayers().addAll(players);

    return game;
  }
}
