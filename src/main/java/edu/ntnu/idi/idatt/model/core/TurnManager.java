package edu.ntnu.idi.idatt.model.core;


import edu.ntnu.idi.idatt.model.game.Player;
import java.util.Iterator;
import java.util.List;

/**
 * Manages the turns of players in a game. This class provides functionality to iterate through a
 * list of players,
 */
public class TurnManager {

  private List<Player> players;
  private Iterator<Player> iterator;

  /**
   * Constructs a TurnManager with the specified list of players.
   *
   * @param players the list of players
   * @throws IllegalArgumentException if the player list is null or empty
   */
  public TurnManager(List<Player> players) {
    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Player list cannot be null or empty");
    }
    this.players = players;
    this.iterator = players.iterator();
  }

  /**
   * Returns the next player in the turn order. If the end of the list is reached, it resets the
   * iterator to start from the beginning.
   *
   * @return the next player
   */
  public Player nextPlayer() {
    if (!iterator.hasNext()) {
      reset();
    }
    return iterator.next();
  }

  /**
   * Resets the iterator to the beginning of the player list. This method is called when the end of
   * the list is reached
   */
  public void reset() {
    this.iterator = players.iterator();
  }

}
