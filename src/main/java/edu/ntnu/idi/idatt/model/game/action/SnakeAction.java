package edu.ntnu.idi.idatt.model.game.action;

import edu.ntnu.idi.idatt.model.game.Player;


public class SnakeAction implements TileAction {
  private final int destination;
  public SnakeAction(int destination) {
    if (destination <= 0) throw new IllegalArgumentException("Destination must be positive");
    this.destination = destination;
  }

  @Override
  public void perform(Player player) {
    if (player == null) throw new NullPointerException("Player cannot be null");
    player.placeOnTile(player.getBoardGame().getBoard().getTile(destination));
  }
}