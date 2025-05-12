package edu.ntnu.idi.idatt.model.game;

public class PointTile extends Tile {

  private final int points;

  public PointTile(int tileId, int points) {
    super(tileId);
    this.points = points;
  }

  public int getPoints() {
    return points;
  }

}
