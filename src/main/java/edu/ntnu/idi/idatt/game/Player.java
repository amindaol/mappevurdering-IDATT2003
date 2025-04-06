package edu.ntnu.idi.idatt.game;


/**
 * Represents a player in a board game.
 */
public class Player {

  private final String name;
  private Tile currentTile;
  private final BoardGame boardGame;

  /**
   * Constructs a player with a name and a reference to the board game it is part of. Player should
   * also know what tile it is currently on.
   *
   * @param name      name of the player.
   * @param boardGame the board game.
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
   * Places the player on a certain tile.
   *
   * @param tile new tile for the player to be placed on.
   */
  public void placeOnTile(Tile tile) {
    if (tile == null) {
      throw new NullPointerException("Tile cannot be null when placing player.");
    }

    this.currentTile = tile;
  }

  /**
   * @param steps
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
   * Returns the tile the player is currently on
   *
   * @return current tile
   */
  public Tile getCurrentTile() {
    return currentTile;
  }

  public String getName() {
    return name;
  }
}
