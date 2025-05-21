package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.action.TileAction;
import java.util.Optional;

/**
 * Represents a single tile on the game board. A tile can link to a next tile and optionally have a
 * special action that is triggered when a player lands on it.
 */
public class Tile {

  private Tile nextTile;
  private final int tileId;
  private int row;
  private int col;
  private TileAction action;

  /**
   * Constructs a new tile with a unique ID.
   *
   * @param tileId the unique identifier of the tile.
   * @param row    the row position of the tile on the board.
   * @param col    the column position of the tile on the board.
   */
  public Tile(int tileId, int row, int col) {
    this.tileId = tileId;
    this.row = row;
    this.col = col;
  }

  /**
   * Sets the next tile that follows this one. This is useful for defining the flow of the board.
   *
   * @param nextTile the tile that follows this one.
   */
  public void setNextTile(Tile nextTile) {
    this.nextTile = nextTile;
  }

  /**
   * Returns the ID of this tile.
   *
   * @return the tile ID.
   */
  public int getTileId() {
    return tileId;
  }

  /**
   * Returns the next tile linked to this one.
   *
   * @return the next tile, or null if there is none.
   */
  public Tile getNextTile() {
    return nextTile;
  }

  /**
   * Sets the action to be performed when a player lands on this tile.
   *
   * @param action the action to associate with this tile.
   */
  public void setAction(TileAction action) {
    this.action = action;
  }

  /**
   * Returns the action assigned to this tile when landed on.
   *
   * @return the TileAction, or null if none is set
   */
  public TileAction getAction() {
    return action;
  }

  /**
   * Performs the action associated with this tile when a player lands on it. This method also
   * places the player on the tile.
   *
   * @param player the player who landed on the tile.
   */
  public void onLand(Player player) {
    player.placeOnTile(this);
    if (action != null) {
      action.perform(player);
    }
  }

  /**
   * Returns the row position of this tile on the board.
   *
   * @return the row position.
   */
  public int getRow() {
    return row;
  }

  /**
   * Sets the row position of this tile on the board.
   *
   * @param row the new row position.
   */
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Returns the column position of this tile on the board.
   *
   * @return the column position.
   */
  public int getCol() {
    return col;
  }

  /**
   * Sets the column position of this tile on the board.
   *
   * @param col the new column position.
   */
  public void setCol(int col) {
    this.col = col;
  }

}
