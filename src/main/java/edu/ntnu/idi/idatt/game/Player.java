package edu.ntnu.idi.idatt.game;

import java.util.stream.Stream;

/**
 * Represents a player in a board game.
 */
public class Player {

  private String name;
  Tile currentTile;

  /**
   * Constructs a player with a name and a reference to the board game it is part of.
   * Player should also know what tile it is currently on.
   *
   * @param name name of the player
   */
  public Player(String name) {̉̉
    this.name = name;
    this.currentTile = null;
  }


  /**
   * Places the player on a certain tile.
   *
   * @param tile new tile for the player to be placed on
   */
  public void placeOnTile(Tile tile) {
    this.currentTile = tile;
  }



  public void move(int steps) {
    
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
