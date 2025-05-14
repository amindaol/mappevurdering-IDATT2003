package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;

public class ModifyPointsAction implements TileAction {

  private final int points;

  public ModifyPointsAction(int points) {
    this.points = points;
  }

  @Override
  public void perform(Player player) {
    player.addPoints(points);
  }

  public int getPoints() {
    return points;
  }
}
