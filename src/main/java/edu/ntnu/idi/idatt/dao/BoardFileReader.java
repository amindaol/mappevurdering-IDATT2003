package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Board;
import java.nio.file.Path;

/**
 * Reads a Board from a file.
 */
public interface BoardFileReader {

  /**
   * Reads the board configuration from the given file path.
   *
   * @param path the Path to the JSON (or other) file
   * @return a Board instance built from that file
   * @throws DaoException on I/O or format error
   */
  Board readBoard(Path path) throws DaoException;
}
