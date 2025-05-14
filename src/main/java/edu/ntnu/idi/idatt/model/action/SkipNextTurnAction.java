package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;

public class SkipNextTurnAction implements TileAction {
  @Override
  public void perform(Player player) {
    if (player == null) throw new NullPointerException("Player cannot be null");
    player.setSkipNextTurn(true);
  }
}