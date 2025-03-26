package edu.ntnu.idi.idatt.game;

public class Tile {

  private Tile nextTile;
  private int tileId;
  private TileAction landAction;

  public Tile(int tileId) {
    this.tileId = tileId;
  }

  public void landPlayer(Player player) {

  }

  public void leavePlayer(Player player) {

  }

  public void setTile(Tile nextTile) {

  }

  public int getTileId() {
    return tileId;
  }
}
