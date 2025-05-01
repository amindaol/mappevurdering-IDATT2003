package edu.ntnu.idi.idatt.model.game.action;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;

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
   * @throws IllegalArgumentException if {@code destinationTileId} is not positive.
   * @throws NullPointerException     if {@code description} is {@code null}.
   */
  public LadderAction(int destinationTileId, String description) {
    if (destinationTileId <= 0) {
      throw new IllegalArgumentException("Destination tile id must be positive");
    }

    if (description == null) {
      throw new NullPointerException("Description cannot be null");
    }

    this.destinationTileId = destinationTileId;
    this.description = description;
  }

  /**
   * Performs the ladder action on the given player. This method moves the player directly to the
   * destination tile.
   *
   * @param player the player who landed on the tile with this action.
   * @throws NullPointerException     if {@code player} is {@code null}.
   * @throws IllegalArgumentException if the destination tile does not exist.
   */
  @Override
  public void perform(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null when performing LadderAction.");
    }

    Tile destination = player.getBoardGame().getBoard().getTile(destinationTileId);
    if (destination == null) {
      throw new IllegalArgumentException("Destination tile does not exist");
    }

    player.placeOnTile(destination);
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
