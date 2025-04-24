package edu.ntnu.idi.idatt.model.dao;

import edu.ntnu.idi.idatt.model.game.Board;
import java.nio.file.Path;

/**
 * Writes a Board to a file.
 */
public interface BoardFileWriter {

  /**
   * Serializes the given Board and writes it to the given file path.
   *
   * @param path  the Path to write the JSON (or other) file to
   * @param board the Board instance to serialize
   * @throws DaoException on I/O error
   */
  void writeBoard(Path path, Board board) throws DaoException;

}
