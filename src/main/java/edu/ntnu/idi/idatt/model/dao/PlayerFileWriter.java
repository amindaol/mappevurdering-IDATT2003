package edu.ntnu.idi.idatt.model.dao;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.nio.file.Path;
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
  void writePlayers(Path csvFile, List<Player> players) throws DaoException;
}

