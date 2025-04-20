package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Board;

/**
 * DAO interface for reading and writing Board configurations.
 */
public interface BoardDao {

  /**
   * Reads a Board configuration from persistent storage.
   *
   * @return the loaded Board instance
   * @throws DaoException if an I/O or parsing error occurs
   */
  Board readBoard() throws DaoException;

  /**
   * Writes the given Board configuration to persistent storage.
   *
   * @param board the Board to serialize
   * @throws DaoException if an I/O error occurs during writing
   */
  void writeBoard(Board board) throws DaoException;

}
