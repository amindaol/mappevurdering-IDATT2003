package edu.ntnu.idi.idatt.dao;

import java.util.List;
import edu.ntnu.idi.idatt.game.Player;

/**
 * Data Access Object interface for reading and writing Player entities.
 */
public interface PlayerDao {

  /**
   * Reads all players from persistent storage.
   *
   * @return a List of Player objects
   * @throws DaoException if an error occurs during the read operation
   */
  List<Player> readPlayers() throws DaoException;

  /**
   * Writes a list of players to persistent storage.
   *
   * @param players the List of Player objects to persist
   * @throws DaoException if an error occurs during the write operation
   */
  void writePlayers(List<Player> players) throws DaoException;
}

