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
 *
 *  @author Aminda Lunde
 *  @author Ingrid Opheim
 *  @version 1.0
 */
public final class BoardGameFactory {

  private BoardGameFactory() {
    // prevent instantiation
  }

  /**
   * Creates a {@link BoardGame} instance from the specified JSON and CSV files.
   *
   * @param boardJson  the path to the board JSON file
   * @param playersCsv the path to the players CSV file
   * @return a new {@link BoardGame} instance
   * @throws DaoException if an error occurs while reading the files
   */
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

  /**
   * Creates a {@link BoardGame} instance based on the specified game mode.
   *
   * @param mode the game mode
   * @return a new {@link BoardGame} instance
   * @throws IllegalArgumentException if the game mode is not supported
   */
  public static BoardGame createGame(GameMode mode) {
    return switch (mode) {
      case LOVE_AND_LADDERS -> createLoveAndLaddersGame();
      case BESTIE_POINT_BATTLES -> createBestiePointBattlesGame();
      default -> throw new IllegalArgumentException("Unsupported game mode: " + mode);
    };
  }

  /**
   * Creates a default list of players for the game.
   *
   * @return a list of default players
   */
  private static List<Player> createDefaultPlayers() {
    return List.of(
        new Player("Aminda", new Token("Heart", "heart.png"),
            LocalDate.of(2005, 11, 5)),
        new Player("Ingrid", new Token("Star", "star.png"),
            LocalDate.of(2002, 8, 28))
    );
  }


  /**
   * Creates a {@link BoardGame} instance for the "Love and Ladders" game.
   *
   * @return a new {@link BoardGame} instance
   */
  public static BoardGame createLoveAndLaddersGame() {
    return createLoveAndLaddersGame(createDefaultPlayers());
  }

  /**
   * Creates a {@link BoardGame} instance for the "Love and Ladders" game with the specified
   * players
   *
   * @param players the list of players
   * @return a new {@link BoardGame} instance
   */
  public static BoardGame createLoveAndLaddersGame(List<Player> players) {
    Board board = BoardFactory.createLoveAndLaddersBoard();
    Dice dice = new Dice(2);
    return assembleGame(board, dice, players);
  }

  /**
   * Creates a {@link BoardGame} instance for the "Bestie Point Battles" game.
   *
   * @return a new {@link BoardGame} instance
   */
  public static BoardGame createBestiePointBattlesGame() {
    return createBestiePointBattlesGame(createDefaultPlayers());
  }

  /**
   * Creates a {@link BoardGame} instance for the "Bestie Point Battles" game with the specified
   * players.
   *
   * @param players the list of players
   * @return a new {@link BoardGame} instance
   */
  public static BoardGame createBestiePointBattlesGame(List<Player> players) {
    Board board = BoardFactory.createBestiePointBattlesBoard();
    Dice dice = new Dice(2);
    return assembleGame(board, dice, players);
  }

  /**
   * Assembles a {@link BoardGame} instance with the specified board, dice, and players.
   *
   * @param board   the game board
   * @param dice    the dice to be used in the game
   * @param players the list of players
   * @return a new {@link BoardGame} instance
   */
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
