package edu.ntnu.idi.idatt.io;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.io.InputStream;

/**
 * Reads a Board from a file.
 */
public interface BoardFileReader {

  /**
   * Reads the board configuration from the given file path.
   *
   * @return a Board instance built from that file
   * @throws DaoException on I/O or format error
   */
  Board readBoard(InputStream stream) throws DaoException;
}
