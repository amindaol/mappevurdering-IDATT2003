package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.io.reader.BoardFileReaderGson;
import edu.ntnu.idi.idatt.io.reader.PlayerFileReaderCsv;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
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

  public static BoardGame createFromFiles(Path boardJson, Path playersCsv) throws DaoException {
    BoardFileReaderGson boardReader = new BoardFileReaderGson();
    PlayerFileReaderCsv playerReader = new PlayerFileReaderCsv();

    Board board = boardReader.readBoard(boardJson);
    List<Player> players = playerReader.readPlayers(playersCsv);
    Dice dice = new Dice(1);

    BoardGame game = new BoardGame(board, dice);
    players.forEach(player -> game.addPlayer(player));
    return game;
  }

  public static BoardGame createSimpleLadderGame(GameMode mode) {
    Board board = new Board();
    for (int i = 1; i <= 30; i++) {
      board.addTile(new Tile(i));
    }
    for (int i = 1; i <= 30; i++) {
      board.getTile(i).setNextTile(board.getTile(i - 1));
    }
    return new BoardGame(board, new Dice(1));
  }
}
