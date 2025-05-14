package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;

public class AddPointsAction implements TileAction {
  private final int points;

  public AddPointsAction(int points) {
    if (points < 0) throw new IllegalArgumentException("Points must be positive");
    this.points = points;
  }

  @Override
  public void perform(Player player) {
    if (player == null) throw new NullPointerException("Player cannot be null");
    player.addPoints(points);
  }

  public int getPoints() {
    return points;
  }
}

