package edu.ntnu.idi.idatt.model.game.action;

import edu.ntnu.idi.idatt.model.game.Player;

public class RemovePointsAction implements TileAction {
  private final int points;

  public RemovePointsAction(int points) {
    if (points < 0) throw new IllegalArgumentException("Points must be positive");
    this.points = points;
  }

  @Override
  public void perform(Player player) {
    if (player == null) throw new NullPointerException("Player cannot be null");
    player.addPoints(-points);
  }

  public int getPoints() {
    return points;
  }
}

