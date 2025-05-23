package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;

/**
 * Represents an action that jumps the player to a specific tile. This action is used when a player
 * lands on a tile that has a jump action, like a ladder.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class JumpToTileAction implements TileAction {

  private final Tile destination;

  /**
   * Constructs a JumpToTileAction with the specified destination tile.
   *
   * @param destination the tile to jump to
   * @throws NullPointerException if {@code destination} is {@code null}
   */
  public JumpToTileAction(Tile destination) {
    if (destination == null) {
      throw new NullPointerException("Destination tile cannot be null.");
    }
    this.destination = destination;
  }

  /**
   * Performs the jump action, moving the player to the destination tile.
   *
   * @param player the player who is jumping
   */
  @Override
  public void perform(Player player) {
    destination.onLand(player);
  }

  /**
   * Returns the destination tile of this jump action.
   *
   * @return the destination tile
   */
  public Tile getDestination() {
    return destination;
  }


}
