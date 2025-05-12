package edu.ntnu.idi.idatt.model.game;


import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidMoveException;
import java.time.LocalDate;

/**
 * Represents a player in a board game.
 */
public class Player {

  private boolean skipNextTurn = false;
  private final String name;
  private String token;
  private Tile currentTile;
  private BoardGame boardGame;
  private LocalDate birthday;
  private int points = 0;


  /**
   * Primary constructor used by the game logic.
   *
   * @param name      the name of the player.
   * @param boardGame the board game the player belongs to.
   * @param birthday the birthday of a player.
   * @throws NullPointerException if {@code name} or {@code boardGame} is {@code null}.
   */
  public Player(String name, BoardGame boardGame, LocalDate birthday) {
    if (name == null) {
      throw new NullPointerException("Player name cannot be null.");
    }
    if (boardGame == null) {
      throw new NullPointerException("BoardGame reference cannot be null.");
    }

    this.name = name;
    this.boardGame = boardGame;
    this.currentTile = null;
    this.birthday = birthday;
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
   * @return the current tile, or null if the player has not been placed yet.
   */
  public Tile getCurrentTile() {
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

  /**
   * Checks whether the player should skip their next turn.
   *
   * @return true if the player must skip next turn, false otherwise
   */
  public boolean isSkipNextTurn() {
    return skipNextTurn;
  }

  /**
   * Sets whether the player should skip their next turn.
   *
   * @param skipNextTurn true to skip the next turn, false otherwise
   */
  public void setSkipNextTurn(boolean skipNextTurn) {
    this.skipNextTurn = skipNextTurn;
  }

  /**
   * Adds points to the player's current score. Can be negative to subtract.
   *
   * @param amount the number of points to add or subtract
   */
  public void addPoints(int amount) {
    this.points += amount;
  }

  /**
   * Gets the player's current point total.
   *
   * @return the number of points the player has
   */
  public int getPoints() {
    return points;
  }

  /**
   * Returns the player's birthday.
   *
   * @return the player's birth date
   */
  public LocalDate getBirthday() {
    return birthday;
  }

  /**
   * Sets the player's token identifier (e.g., "cat", "unicorn").
   *
   * @param token the name of the token image (without path)
   * @throws NullPointerException if token is null
   */
  public void setToken(String token) {
    if (token == null) {
      throw new NullPointerException("Token cannot be null.");
    }
    this.token = token;
  }

}
