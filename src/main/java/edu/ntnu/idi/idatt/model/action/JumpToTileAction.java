package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;

public class JumpToTileAction implements TileAction {

  private final Tile destination;

  public JumpToTileAction(Tile destination) {
    this.destination = destination;
  }

  @Override
  public void perform(Player player) {
    destination.onLand(player);
  }


}
