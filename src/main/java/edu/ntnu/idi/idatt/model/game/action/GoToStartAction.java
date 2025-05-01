package edu.ntnu.idi.idatt.model.game.action;

import edu.ntnu.idi.idatt.model.game.Player;

public class GoToStartAction implements TileAction {

  @Override
  public void perform(Player player) {
    if (player == null)
      throw new NullPointerException("Player cannot be null");
    player.placeOnTile(player.getBoardGame().getBoard().getTile(1));
  }

}