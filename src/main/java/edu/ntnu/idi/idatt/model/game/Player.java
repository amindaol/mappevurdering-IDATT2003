package edu.ntnu.idi.idatt.model.game;


import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidMoveException;
import java.time.LocalDate;

/**
 * Represents a player in a board game.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class Player {

  private final String name;
  private Token token;
  private Tile currentTile;
  private final LocalDate birthday;
  private int points = 0;
  private boolean skipNextTurn = false;

  /**
   * Primary constructor used by the game logic.
   *
   * @param name     the name of the player.
   * @param token    the token belonging to the player.
   * @param birthday the birthday of a player.
   * @throws NullPointerException if {@code name} is {@code null}.
   */
  public Player(String name, Token token, LocalDate birthday) {
    if (name == null) {
      throw new NullPointerException("Player name cannot be null.");
    }

    this.name = name;
    this.token = token;
    this.birthday = birthday;
  }


  /**
   * Places the player on a specific tile.
   *
   * @param tile the tile to place the player on.
   * @throws NullPointerException if {@code tile} is {@code null}.
   */
  public void placeOnTile(Tile tile) {
    if (tile == null) {
      throw new NullPointerException("Tile cannot be null when placing player.");
    }

    this.currentTile = tile;
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
   * Returns the player's token identifier.
   *
   * @return the token.
   */
  public Token getToken() {
    return token;
  }

  /**
   * Returns a string representation of the player.
   *
   * @return string representation of the player
   */
  @Override
  public String toString() {
    return "Player{name='" + name + "', token=" + token + ", tile=" +
        (currentTile != null ? currentTile.getTileId() : "none") + '}';
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
   * @return the player's birthdate
   */
  public LocalDate getBirthday() {
    return birthday;
  }

  /**
   * Sets the player's token identifier (e.g., "flower", "cloud").
   *
   * @param token the name of the token image (without path)
   * @throws NullPointerException if token is null
   */
  public void setToken(Token token) {
    if (token == null) {
      throw new NullPointerException("Token cannot be null.");
    }
    this.token = token;
  }

  /**
   * Sets the current tile of the player.
   *
   * @param tile the tile to set as the current tile
   * @throws NullPointerException if current tile is null
   */
  public void setCurrentTile(Tile tile) {
    if (tile == null) {
      throw new NullPointerException("Current tile cannot be null.");
    }
    this.currentTile = tile;
  }
}
