package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.BestiePlayer;

/**
 * Lets the player buy a star if they have enough coins.
 */
public class BuyStarAction implements TileAction {

  private final int price;

  /**
   * Constructs a BuyStarAction with the specified price.
   *
   * @param price the price of the star
   */
  public BuyStarAction(int price) {
    this.price = price;
  }

  /**
   * Performs the action of buying a star for the player.
   *
   * @param player the player who is buying the star
   */
  @Override
  public void perform(Player player) {
    if (player instanceof BestiePlayer bp && bp.getCoins() >= price) {
      bp.removeCoins(price);
      bp.addStar();
    }
  }
}