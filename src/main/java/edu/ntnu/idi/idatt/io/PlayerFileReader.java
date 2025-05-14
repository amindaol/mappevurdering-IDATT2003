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
   * Reads a list of Player instances from a file.
   *
   * @param path Path to a CSV or other supported format
   * @return list of Player objects
   * @throws DaoException if file reading or parsing fails
   */
  List<Player> readPlayers(Path path) throws DaoException;

}
