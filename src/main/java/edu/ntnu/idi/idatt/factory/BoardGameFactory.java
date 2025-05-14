package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.io.BoardFileReaderGson;
import edu.ntnu.idi.idatt.io.PlayerFileReaderCsv;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.GameMode;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.PointBoardGame;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Factory for creating {@link BoardGame} instances, either standard or configured from files.
 */
public final class BoardGameFactory {

  private BoardGameFactory() {
    // prevent instantiation
  }

  public static BoardGame createStandardBoardGame() {
    Board board = BoardFactory.createDefaultBoard();
    Dice dice = new Dice(1);
    return new BoardGame(board, dice);
  }

  public static BoardGame createGameFromFiles(Path boardJsonPath, Path playersCsvPath) throws DaoException {
    Board board = new BoardFileReaderGson().readBoard(boardJsonPath);
    List<Player> players = new PlayerFileReaderCsv().readPlayers(playersCsvPath);

    Dice dice = new Dice(1);
    BoardGame game = new BoardGame(board, dice);

    for (Player player : players) {
      game.addPlayer(player);
    }

    return game;
  }

  public static BoardGame createGame(GameMode mode) {
    Board board = switch (mode) {
      case LOVE_AND_LADDERS -> BoardFactory.createLoveAndLaddersBoard();
      case BESTIE_POINT_BATTLES -> BoardFactory.createBestiePointBattlesBoard();
    };

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

    Path path = Path.of("src/main/resources/boards", fileName);

    if (!Files.exists(path)) {
      throw new RuntimeException("Could not find board file: " + fileName);
    }

    return new BoardFileReaderGson().readBoard(path);
  }
}
