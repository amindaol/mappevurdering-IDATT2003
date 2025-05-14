package edu.ntnu.idi.idatt.io.reader;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.util.exceptionHandling.DaoException;
import java.nio.file.Path;


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
  Board readBoard(Path path) throws DaoException;
}
