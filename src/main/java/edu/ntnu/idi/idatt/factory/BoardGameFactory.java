package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.io.reader.BoardFileReaderGson;
import edu.ntnu.idi.idatt.io.reader.PlayerFileReaderCsv;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.model.game.Token;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
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
    players.forEach(game::addPlayer);
    return game;
  }

  public static BoardGame createGame(GameMode mode) {
    return switch (mode) {
      case LOVE_AND_LADDERS -> createLoveAndLaddersGame();
      case BESTIE_POINT_BATTLES -> createBestiePointBattlesGame();
      default -> throw new IllegalArgumentException("Unsupported game mode: " + mode);
    };
  }

  private static List<Player> createDefaultPlayers() {
    return List.of(
        new Player("Aminda", new Token("Heart", "heart.png"),
            LocalDate.of(2005, 11, 5)),
        new Player("Ingrid", new Token("Star", "star.png"),
            LocalDate.of(2002, 8,28))
    );

  }


  public static BoardGame createLoveAndLaddersGame() {
    return createLoveAndLaddersGame(createDefaultPlayers());
  }

  public static BoardGame createLoveAndLaddersGame(List<Player> players) {
    Board board = BoardFactory.createLoveAndLaddersBoard();
    Dice dice = new Dice(2);
    return assembleGame(board, dice, players);
  }

  public static BoardGame createBestiePointBattlesGame() {
    return createBestiePointBattlesGame(createDefaultPlayers());
  }

  public static BoardGame createBestiePointBattlesGame(List<Player> players) {
    Board board = BoardFactory.createBestiePointBattlesBoard();
    Dice dice = new Dice(2);
    return assembleGame(board, dice, players);
  }

  private static BoardGame assembleGame(Board board, Dice dice, List<Player> players) {
    BoardGame game = new BoardGame(board, dice);
    Tile startTile = board.getStartTile();

    for (Player player : players) {
      player.setCurrentTile(startTile);
      game.addPlayer(player);
    }

    return game;
  }

}
