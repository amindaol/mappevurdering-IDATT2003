package edu.ntnu.idi.idatt.game;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game board consisting of tiles.
 * <p>
 * Each tile is identified by a unique integer ID and stored in a map for easy access. This class
 * provides methods to add tiles and retrieve them by their ID.
 */
public class Board {

  private Map<Integer, Tile> tiles;

  /**
   * Constructs an empty game board.
   */
  public Board() {
    this.tiles = new HashMap<>();
  }

  /**
   * Adds a tile to the board. If a tile with the same ID already exists, it will be overwritten.
   *
   * @param tile the tile to add to the board.
   */
  public void addTile(Tile tile) {
    tiles.put(tile.getTileId(), tile);
  }

  /**
   * Retrieves a tile from the board based on its tile ID.
   *
   * @param tileId the ID of the tile to retrieve.
   * @return the Tile with the specified ID, or null if it does not exist.
   */
  public Tile getTile(int tileId) {
    return tiles.get(tileId);
  }

  /**
   * Returns the number of tiles currently on the board.
   *
   * @return the number of tiles.
   */
  public int size() {
    return tiles.size();
  }
}
