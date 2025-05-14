package edu.ntnu.idi.idatt.io;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.nio.file.Path;
import java.util.List;


/**
 * Reads a list of Players from a CSV file.
 */
public interface PlayerFileReader {

  /**
   * Reads all players from persistent storage.
   *
   * @return a List of Player objects
   * @throws DaoException if an error occurs during the read operation
   */
  List<Player> readPlayers(Path csvFile) throws DaoException;


}
