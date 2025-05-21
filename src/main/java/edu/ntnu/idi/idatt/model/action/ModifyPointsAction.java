package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;

/**
 * Represents an action that modifies the points of a player. This action is used when a player
 * lands on a tile that has a point-modifying action.
 */
public class ModifyPointsAction implements TileAction {

  private final int points;

  /**
   * Constructs a ModifyPointsAction with the specified number of points to modify.
   *
   * @param points the number of points to modify
   */
  public ModifyPointsAction(int points) {
    this.points = points;
  }

  /**
   * Performs the action, modifying the player's points.
   *
   * @param player the player whose points are to be modified
   */
  @Override
  public void perform(Player player) {
    player.addPoints(points);
  }

  /**
   * Returns the number of points to modify.
   *
   * @return the number of points
   */
  public int getPoints() {
    return points;
  }
}
