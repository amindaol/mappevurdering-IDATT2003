package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidLadderException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidMoveException;
import edu.ntnu.idi.idatt.util.exceptionHandling.TileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the game board consisting of tiles. Each tile is identified by a unique integer ID and
 * stored in a map for easy access. This class provides methods to add tiles and retrieve them by
 * their ID.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class Board {

  private final Map<Integer, Tile> tiles;
  private final int rows;
  private final int cols;
  private Tile startTile;
  private List<Ladder> ladders = new ArrayList<>();
  private List<Ladder> snakes = new ArrayList<>();


  /**
   * Constructs an empty game board.
   *
   * @param rows the number of rows on the board
   * @param cols the number of columns on the board
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
   * @throws TileNotFoundException if no tile exists with the given ID
   */
  public Tile getTile(int tileId) {
    Tile tile = tiles.get(tileId);
    if (tile == null) {
      throw new TileNotFoundException("Tile not found: " + tileId);
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
   * Returns the last tile on the board, which is the tile with the highest ID.
   *
   * @return the tile with the highest ID on the board
   * @throws InvalidMoveException if the board is empty
   */
  public Tile getLastTile() {
    return tiles.entrySet().stream()
        .max(Map.Entry.comparingByKey())
        .map(Map.Entry::getValue)
        .orElseThrow(() -> new InvalidMoveException("Board is empty"));
  }

  /**
   * Returns all tiles on the board as a lis.
   *
   * @return a list of all tiles on the board.
   */
  public List<Tile> getTiles() {
    return new ArrayList<>(tiles.values());
  }

  /**
   * Method to get the start tile.
   *
   * @return the start tile.
   */
  public Tile getStartTile() {
    if (startTile == null) {
      throw new InvalidMoveException("Start tile not set.");
    }
    return startTile;
  }

  /**
   * Sets the start tile for the game. This tile is where players are placed at the beginning of the
   * game.
   *
   * @param tile the tile to set as the start tile
   */
  public void setStartTile(Tile tile) {
    if (tile == null) {
      throw new NullPointerException("Start tile cannot be null.");
    }
    this.startTile = tile;
  }


  /**
   * Returns all tiles on the board in order of their IDs.
   *
   * @return a list of tiles ordered by their IDs
   */
  public List<Tile> getTilesOrdered() {
    return tiles.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .map(Map.Entry::getValue)
        .toList();
  }

  /**
   * Returns the list of ladders on the board.
   *
   * @return list of ladders
   */
  public List<Ladder> getLadders() {
    return ladders;
  }

  /**
   * Sets the list of ladders on the board.
   *
   * @param ladders list of ladders to set
   */
  public void setLadders(List<Ladder> ladders) {
    if (ladders == null) {
      throw new InvalidLadderException("Ladders list cannot be null.");
    }
    this.ladders = ladders;
  }

  /**
   * Returns the ladder that starts on the given tile, if any.
   *
   * @param tileId the ID of the tile to check
   * @return the ladder starting from the tile, or null if none
   */
  public Ladder getLadderFromTile(int tileId) {
    for (Ladder ladder : ladders) {
      if (ladder.getFromTileId() == tileId) {
        return ladder;
      }
    }
    return null;
  }

  /**
   * Returns the list of snakes on the board.
   *
   * @return list of snakes
   */
  public List<Ladder> getSnakes() {
    return snakes;
  }

  /**
   * Returns the number of rows in the board.
   *
   * @return row count
   */
  public int getRows() {
    return rows;
  }

  /**
   * Returns the number of columns in the board.
   *
   * @return column count
   */
  public int getCols() {
    return cols;
  }

  /**
   * Sets the list of snakes on the board.
   *
   * @param snakes list of snakes to set
   * @throws InvalidLadderException if {@code snakes} is {@code null}.
   */
  public void setSnakes(List<Ladder> snakes) {
    if (snakes == null) {
      throw new InvalidLadderException("Snakes list cannot be null.");
    }
    this.snakes = snakes;
  }
}
