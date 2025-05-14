package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;

public class ModifyPointsAction implements TileAction {

  private final int delta;

  public ModifyPointsAction(int delta) {
    this.delta = delta;
  }

  @Override
  public void perform(Player player) {
    player.addPoints(delta);
  }
}
