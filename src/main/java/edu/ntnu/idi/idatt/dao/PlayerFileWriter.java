package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Player;
import java.util.List;


/**
 * Writes a list of Players to a CSV file.
 */
public interface PlayerFileWriter {

  /**
   * Writes a list of players to persistent storage.
   *
   * @param players the List of Player objects to persist
   * @throws DaoException if an error occurs during the write operation
   */
  void writePlayers(List<Player> players) throws DaoException;
}

