package edu.ntnu.idi.idatt.game;


/**
 * Represents a player in a board game.
 */
public class Player {

  private final String name;
  private Tile currentTile;
  private final BoardGame boardGame;

  /**
   * Constructs a player with a name and a reference to the board game it is part of. The player
   * initially has no tile assigned.
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
   * Moves the player a given number of steps along the board. Movement follows the next tiles. If
   * there are no more tiles, the player stops.
   *
   * @param steps the number of steps to move.
   * @throws IllegalStateException if the player is not currently placed on any tile.
   */
  public void move(int steps) {
    if (currentTile == null) {
      throw new IllegalStateException("Cannot move player: Player is not on any tile.");
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
   * @return the current tile
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
}
