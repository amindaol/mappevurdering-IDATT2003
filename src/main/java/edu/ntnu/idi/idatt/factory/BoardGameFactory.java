package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.model.game.Token;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.UnsupportedGameModeException;
import java.time.LocalDate;
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
   * Creates a {@link BoardGame} instance based on the specified game mode.
   *
   * @param mode the game mode
   * @return a new {@link BoardGame} instance
   * @throws GameNotInitializedException if game mode is null
   * @throws UnsupportedGameModeException if the game mode is not supported
   */
  public static BoardGame createGame(GameMode mode) {
    if (mode == null) {
      throw new GameNotInitializedException("Game mode cannot be null");
    }
    return switch (mode) {
      case LOVE_AND_LADDERS -> createLoveAndLaddersGame();
      case BESTIE_POINT_BATTLES -> createBestiePointBattlesGame();
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
   * players.
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
