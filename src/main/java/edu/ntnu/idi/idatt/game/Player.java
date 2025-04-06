package edu.ntnu.idi.idatt.game;


/**
 * Represents a player in a board game.
 */
public class Player {

  private String name;
  private Tile currentTile;
  private BoardGame boardGame;

  /**
   * Constructs a player with a name and a reference to the board game it is part of. Player should
   * also know what tile it is currently on.
   *
   * @param name      name of the player.
   * @param boardGame the boardgame.
   */
  public Player(String name, BoardGame boardGame) {
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
    this.currentTile = tile;
  }


  public void move(int steps) {
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
