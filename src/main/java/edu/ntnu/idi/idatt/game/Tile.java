package edu.ntnu.idi.idatt.game;

/**
 * Represents a single tile on the game board.
 * <p>
 * A tile can link to a next tile and optionally have a special action that is triggered when a
 * player lands on it.
 */
public class Tile {

  private Tile nextTile;
  private int tileId;
  private TileAction landAction;

  /**
   * Constructs a new tile with a unique ID.
   *
   * @param tileId the unique identifier of the tile.
   */
  public Tile(int tileId) {
    this.tileId = tileId;
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
   * Handles logic when a player lands on this tile.
   * <p>
   * The player is placed on this tile, and any associated {@link TileAction} is preformed (such as
   * climbing a ladder).
   *
   * @param player the player landing on this tile.
   */
  public void landPlayer(Player player) {
    player.placeOnTile(this);

    if (landAction != null) {
      landAction.perform(player);
    }
  }

  /**
   * Called when a player leaves this tile.
   * <p>
   * Currently used for logging purposes.
   *
   * @param player the player leaving the tile.
   */
  public void leavePlayer(Player player) {
    System.out.println(player.getName() + " leaves tile " + tileId);
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
   * @param landAction the action to associate with this tile.
   */
  public void setLandAction(TileAction landAction) {
    this.landAction = landAction;
  }
}
