package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.action.TileAction;
import java.util.Optional;

/**
 * Represents a single tile on the game board.
 *
 * <p>A tile can link to a next tile and optionally have a special action that is triggered when a
 * player lands on it.
 */
public class Tile {

  private Tile nextTile;
  private final int tileId;
  private int x;
  private int y;
  private TileAction action;

  /**
   * Constructs a new tile with a unique ID.
   *
   * @param tileId the unique identifier of the tile.
   */
  public Tile(int tileId, int x, int y) {
    this.tileId = tileId;
    this.x = x;
    this.y = y;
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

  public void onLand(Player player) {
    player.placeOnTile(this);
    if (action != null) {
      action.perform(player);
    }
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

}
