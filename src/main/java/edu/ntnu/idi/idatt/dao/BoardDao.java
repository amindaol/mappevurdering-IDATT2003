package edu.ntnu.idi.idatt.dao;

import edu.ntnu.idi.idatt.game.Board;

/**
 * DAO interface for reading and writing Board configurations.
 */
public interface BoardDao {

  Board readBoard() throws DaoException;

  void writeBoard(Board board) throws DaoException;

}
