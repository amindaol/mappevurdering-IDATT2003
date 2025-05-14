package edu.ntnu.idi.idatt.core;

import edu.ntnu.idi.idatt.model.game.*;


public class LinearMovement implements Movement {

  @Override
  public void move(Player player, int steps) {
    Tile current = player.getCurrentTile();

    for (int i = 0; i < steps && current.getNextTile() != null; i++) {
      current = current.getNextTile();
    }

    player.setCurrentTile(current);

    if (current.getAction() != null) {
      current.getAction().perform(player);
    }
  }

}
