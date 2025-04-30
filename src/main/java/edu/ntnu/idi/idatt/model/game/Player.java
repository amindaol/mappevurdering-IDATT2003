package edu.ntnu.idi.idatt.model.game;


import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidMoveException;

/**
 * Represents a player in a board game.
 */
public class Player {

  private final String name;
  private String token;
  private Tile currentTile;
  private BoardGame boardGame;

  /**
   * Primary constructor used by the game logic.
   *
   * @param name      the name of the player.
   * @param boardGame the board game the player belongs to.
   * @throws NullPointerException if {@code name} or {@code boardGame} is {@code null}.
   */
  public Player(String name, BoardGame boardGame) {
    if (name == null) {
      throw new NullPointerException("Player name cannot be null.");
    }
    if (boardGame == null) {
      throw new NullPointerException("BoardGame reference cannot be null.");
    }

    this.name = name;
    this.boardGame = boardGame;
    this.currentTile = null;
  }

  /**
   * Secondary constructor used by DAO (CSV) when loading players. BoardGame reference can be
   * injected later via setter.
   *
   * @param name  the player's name
   * @param token the identifier for the player's token (e.g. "TopHat")
   * @throws NullPointerException if {@code name} is {@code null}
   */
  public Player(String name, String token) {
    if (name == null) {
      throw new NullPointerException("Player name cannot be null.");
    }
    this.name = name;
    this.token = token;
    this.currentTile = null;
    this.boardGame = null;
  }

  /**
   * Associates this player with a specific BoardGame instance.
   *
   * @param boardGame the BoardGame to set
   * @throws NullPointerException if {@code boardGame} is {@code null}
   */
  public void setBoardGame(BoardGame boardGame) {
    if (boardGame == null) {
      throw new NullPointerException("BoardGame reference cannot be null.");
    }
    this.boardGame = boardGame;
  }

  /**
   * Places the player on a specific tile.
   *
   * @param tile the tile to place the player on.
   * @throws NullPointerException if {@code tile} is {@code null}.
   * @throws GameNotInitializedException if the BoardGame is not set.
   */
  public void placeOnTile(Tile tile) {
    if (tile == null) {
      throw new NullPointerException("Tile cannot be null when placing player.");
    }

    if (boardGame == null) {
      throw new GameNotInitializedException();
    }

    this.currentTile = tile;
  }

  /**
   * Moves the player a given number of steps along the board. Movement follows the next tiles. If
   * there are no more tiles, the player stops.
   *
   * @param steps the number of steps to move.
   * @throws GameNotInitializedException if the player has not been placed on a tile
   * @throws InvalidMoveException       if steps is negative
   */
  public void move(int steps) {
    if (currentTile == null) {
      throw new GameNotInitializedException();
    }
    if (steps < 0) {
      throw new InvalidMoveException("Steps cannot be negative: " + steps);
    }

    Tile destination = currentTile;
    for (int i = 0; i < steps; i++) {
      if (destination.getNextTile() != null) {
        destination = destination.getNextTile();
      } else {
        break;
      }
    }
    placeOnTile(destination);
  }

  /**
   * Returns the tile the player is currently on.
   *
   * @return the current tile.
   * @throws GameNotInitializedException if the player has not been placed on any tile.
   */
  public Tile getCurrentTile() {
    if (currentTile == null) {
      throw new GameNotInitializedException();
    }
    return currentTile;
  }

  /**
   * Returns the name of the player.
   *
   * @return the player's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the associated BoardGame instance.
   *
   * @return the BoardGame
   * @throws GameNotInitializedException if no BoardGame has been set
   */
  public BoardGame getBoardGame() {
    if (boardGame == null) {
      throw new GameNotInitializedException();
    }
    return boardGame;
  }

  /**
   * Returns the player's token identifier.
   *
   * @return the token string
   */
  public String getToken() {
    return token;
  }

  @Override
  public String toString() {
    return "Player{name='" + name + "', token='" + token + "'}";
  }
}
