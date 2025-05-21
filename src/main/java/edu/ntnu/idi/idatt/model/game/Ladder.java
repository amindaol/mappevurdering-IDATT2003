package edu.ntnu.idi.idatt.model.game;

public class Ladder {

  private final int fromTileId;
  private final int toTileId;

  public Ladder(int fromTileId, int toTileId) {
    this.fromTileId = fromTileId;
    this.toTileId = toTileId;
  }

  public int getFromTileId() {
    return fromTileId;
  }

  public int getToTileId() {
    return toTileId;
  }

  @Override
  public String toString() {
    return "Ladder{" + fromTileId + " → " + toTileId + '}';
  }

}
