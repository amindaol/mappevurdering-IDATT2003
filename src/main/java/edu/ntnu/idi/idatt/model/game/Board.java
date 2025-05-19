package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidMoveException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the game board consisting of tiles. Each tile is identified by a unique integer ID and
 * stored in a map for easy access. This class provides methods to add tiles and retrieve them by
 * their ID.
 */
public class Board {

  private final Map<Integer, Tile> tiles ;
  private final int rows;
  private final int cols;
  private Tile startTile;
  private Tile endTile;

  /**
   * Constructs an empty game board.
   */
  public Board(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.tiles = new HashMap<>();
  }

  /**
   * Adds a tile to the board. If a tile with the same ID already exists, it will be overwritten.
   *
   * @param tile the tile to add to the board.
   * @throws NullPointerException if {@code tile} is {@code null}.
   */
  public void addTile(Tile tile) {
    if (tile == null) {
      throw new NullPointerException("Tile cannot be null when adding to board.");
    }
    tiles.put(tile.getTileId(), tile);
  }

  /**
   * Retrieves a tile from the board based on its tile ID.
   *
   * @param tileId the ID of the tile to retrieve.
   * @return the Tile with the specified ID, or null if it does not exist.
   * @throws InvalidMoveException if no tile exists with the given ID
   */
  public Tile getTile(int tileId) {
    Tile tile = tiles.get(tileId);
    if (tile == null) {
      throw new InvalidMoveException("Tile not found: " + tileId);
    }
    return tile;
  }

  /**
   * Returns the number of tiles currently on the board.
   *
   * @return the number of tiles.
   */
  public int size() {
    return tiles.size();
  }

  /**
   * @return the tile with the highest ID on the board
   * @throws InvalidMoveException if the board is empty
   */
  public Tile getLastTile() {
    return tiles.entrySet().stream()
        .max(Map.Entry.comparingByKey())
        .map(Map.Entry::getValue)
        .orElseThrow(() -> new InvalidMoveException("Board is empty"));
    }


    public List<Tile> getTiles() {
    return new ArrayList<>(tiles.values());
  }

  public Tile getStartTile() {
    if (startTile == null) {
      throw new InvalidMoveException("Start tile not set.");
    }
    return startTile;
  }

  public void setStartTile(Tile tile) {
    if (tile == null) throw new NullPointerException("Start tile cannot be null.");
    this.startTile = tile;
  }

  public List<Tile> getTilesOrdered() {
    return tiles.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .map(Map.Entry::getValue)
        .toList();
  }
}
