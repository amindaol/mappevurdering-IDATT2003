package edu.ntnu.idi.idatt.model.game;


import java.util.Iterator;
import java.util.List;


public class TurnManager {

  private List<Player> players;
  private Iterator<Player> iterator;

  public TurnManager(List<Player> players) {
    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Player list cannot be null or empty");
    }
    this.players = players;
    this.iterator = players.iterator();
  }

  public Player nextPlayer() {
    if (!iterator.hasNext()) {
      reset();
    }
    return iterator.next();
  }

  public void reset() {
    this.iterator = players.iterator();
  }
}
