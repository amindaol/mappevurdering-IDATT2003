package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import edu.ntnu.idi.idatt.model.game.Player;
import java.util.ArrayList;
import java.util.List;


/**
 * Steals one star from another player (if any have stars).
 */
public class StealStarAction implements TileAction {

  private List<Player> allPlayers;

  public StealStarAction() {
    this.allPlayers = List.of();
  }

  @Override
  public void perform(Player player) {
    if (!(player instanceof BestiePlayer thief)) {
      return;
    }

    for (Player other : allPlayers) {
      if (other != player && other instanceof BestiePlayer target && target.getStars() > 0) {
        target.removeStar();
        thief.addStar();
        break;
      }
    }
  }

  public void setPlayers(List<Player> players) {
    this.allPlayers = new ArrayList<>(players);
  }
}