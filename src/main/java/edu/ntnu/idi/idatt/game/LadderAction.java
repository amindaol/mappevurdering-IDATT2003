package edu.ntnu.idi.idatt.game;

/**
 *
 */
public class LadderAction implements TileAction {

  private final int destinationTileId;
  private final String description;

  /**
   * @param destinationTileId
   * @param description
   */
  public LadderAction(int destinationTileId, String description) {
    this.destinationTileId = destinationTileId;
    this.description = description;
  }

  @Override
  public void perform(Player player) {

  }

  public int getDestinationTileId() {
    return destinationTileId;
  }

  public String getDescription() {
    return description;
  }
}
