package edu.ntnu.idi.idatt.game;

public class Tile {

  private Tile nextTile;
  private int tileId;
  private TileAction landAction;

  public Tile(int tileId) {
    this.tileId = tileId;
  }

  public void setNextTile(Tile nextTile) {
    this.nextTile = nextTile;
  }

  public void landPlayer(Player player) {
    player.placeOnTile(this);

    if (landAction != null) {
      landAction.perform(player);
    }
  }

  public void leavePlayer(Player player) {
    System.out.println(player.getName() + " leaves tile " + tileId);
  }

  public int getTileId() {
    return tileId;
  }

  public Tile getNextTile() {
    return nextTile;
  }

  public void setLandAction(TileAction landAction) {
    this.landAction = landAction;
  }
}
