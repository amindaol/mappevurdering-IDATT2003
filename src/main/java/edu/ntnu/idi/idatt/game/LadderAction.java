package edu.ntnu.idi.idatt.game;

/**
 * Represents a ladder on the game board.¨
 * <p>
 * When a player lands on a tile with a {@code LadderAction}, the player is moved to a new tile
 * specified by the destination tile ID.
 */
public class LadderAction implements TileAction {

  private final int destinationTileId;
  private final String description;

  /**
   * Creates a new LadderAction that moves the player to a specified destination tile.
   *
   * @param destinationTileId the ID of the tile the player should be moved to.
   * @param description       a textual description of the ladder action (e.g., "Climb to tile
   *                          14").
   */
  public LadderAction(int destinationTileId, String description) {
    this.destinationTileId = destinationTileId;
    this.description = description;
  }

  /**
   * Performs the ladder action on the given player.
   * <p>
   * This method should move the player to the destination tile. Implementation will be completed
   * once Player and Board are fully defined.
   *
   * @param player the player who landed on the tile with this action.
   */
  @Override
  public void perform(Player player) {
    // To be implemented later!
  }

  /**
   * Returns the ID of the tile that this ladder leads to.
   *
   * @return the destination tile ID.
   */
  public int getDestinationTileId() {
    return destinationTileId;
  }

  /**
   * Returns a description of the ladder action.
   *
   * @return a string describing this ladder.
   */
  public String getDescription() {
    return description;
  }
}
